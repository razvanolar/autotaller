package com.autotaller.app.components.app_view.search_components_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.utils.extensions.StockTypeCheckBox;
import com.autotaller.app.components.app_view.utils.extensions.UsageTypeCheckBox;
import com.autotaller.app.components.utils.SimpleDialog;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.InjectCarComponentsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.SearchCarComponentsEvent;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.model.SearchComponentModel;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class SearchComponentsMainController implements Controller<SearchComponentsMainController.ISearchComponentsMainView> {

  public interface ISearchComponentsMainView extends View {
    Node getSubkitSearchNode();
    TextField getComponentNameTextField();
    TextField getComponentCodeTextField();
    TextField getComponentMinPriceTextField();
    TextField getComponentMaxPriceTextField();
    Node getSearchComponentsButton();
    List<UsageTypeCheckBox> getUsageCheckBoxList();
    List<StockTypeCheckBox> getStockCheckBoxList();
  }

  private ISearchComponentsMainView view;

  @Override
  public void bind(ISearchComponentsMainView view) {
    this.view = view;

    view.getSubkitSearchNode().setOnMouseClicked(event -> {
      Component component = ComponentFactory.createComponent(ComponentType.SHOW_CAR_KITS_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView(), ComponentType.SHOW_CAR_KITS_VIEW.getTitle()));
        EventBus.fireEvent(new BindLastViewEvent());
      }
    });

    view.getSearchComponentsButton().setOnMouseClicked(event -> {
      if (!isValidPriceSelection()) {
        EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Campurile de pret pot contine doar numere naturale!")));
        return;
      }
      String minText = view.getComponentMinPriceTextField().getText();
      String maxText = view.getComponentMaxPriceTextField().getText();
      int minPrice = StringValidator.isNullOrEmpty(minText) ? 0 : Integer.parseInt(minText);
      int maxPrice = StringValidator.isNullOrEmpty(maxText) ? 0 : Integer.parseInt(maxText);
      List<StockTypeCheckBox> stockCheckBoxList = view.getStockCheckBoxList();
      List<UsageTypeCheckBox> usageCheckBoxList = view.getUsageCheckBoxList();
      List<StockType> stockList = new ArrayList<>();
      List<UsageStateType> usageList = new ArrayList<>();
      for (StockTypeCheckBox checkBox : stockCheckBoxList) {
        if (checkBox.isSelected())
          stockList.add(checkBox.getStockType());
      }
      for (UsageTypeCheckBox checkBox : usageCheckBoxList) {
        if (checkBox.isSelected())
          usageList.add(checkBox.getUsageStateType());
      }

      SearchComponentModel searchModel = new SearchComponentModel(view.getComponentNameTextField().getText().trim(),
              view.getComponentCodeTextField().getText().trim(), usageList, stockList, minPrice, maxPrice);
      EventBus.fireEvent(new SearchCarComponentsEvent(searchModel, carComponents -> {
        Component component = ComponentFactory.createComponent(ComponentType.SEARCH_CAR_COMPONENTS_VIEW);
        if (component != null) {
          EventBus.fireEvent(new AddViewToStackEvent(component.getView(), ComponentType.SEARCH_CAR_COMPONENTS_VIEW.getTitle()));
          EventBus.fireEvent(new InjectCarComponentsEvent(carComponents));
          EventBus.fireEvent(new BindLastViewEvent());
        }
      }));
    });
  }

  private boolean isValidPriceSelection() {
    String minPriceText = view.getComponentMinPriceTextField().getText();
    String maxPriceText = view.getComponentMaxPriceTextField().getText();
    return (StringValidator.isNullOrEmpty(minPriceText) || StringValidator.isPositiveInteger(minPriceText)) &&
            (StringValidator.isNullOrEmpty(maxPriceText) || StringValidator.isPositiveInteger(maxPriceText));
  }
}
