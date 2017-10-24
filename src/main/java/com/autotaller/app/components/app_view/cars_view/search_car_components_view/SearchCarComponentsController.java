package com.autotaller.app.components.app_view.cars_view.search_car_components_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.cars_view.search_car_components_view.utils.PreSellComponentView;
import com.autotaller.app.components.utils.NodeDialog;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.*;
import com.autotaller.app.events.app_view.search_views.AddPreSellComponentEvent;
import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.model.utils.PreSellComponentModel;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Created by razvanolar on 02.06.2017
 */
public class SearchCarComponentsController implements Controller<SearchCarComponentsController.ISearchCarComponentsView> {

  public interface ISearchCarComponentsView extends View {
    TableView<CarComponentModel> getCarComponentsTable();
    Button getSellComponentButton();
  }

  private ISearchCarComponentsView view;
  private int carId;
  private int kitId;
  private List<CarComponentModel> carComponents;

  @Override
  public void bind(ISearchCarComponentsView view) {
    this.view = view;

    view.getSellComponentButton().setOnAction(event -> {
      CarComponentModel selectedComponent = view.getCarComponentsTable().getSelectionModel().getSelectedItem();
      if (selectedComponent == null)
        return;
      PreSellComponentView componentPreview = new PreSellComponentView(selectedComponent);
      NodeDialog nodeDialog = new NodeDialog("Vanzare componenta", "Vinde", componentPreview.asNode());
      EventBus.fireEvent(new ShowDialogEvent(nodeDialog));
      nodeDialog.getConfirmationButton().setOnAction(event1 -> {
        if (componentPreview.isValid()) {
          nodeDialog.close();
          PreSellComponentModel sellModel = componentPreview.collect();
          EventBus.fireEvent(new AddPreSellComponentEvent(sellModel, this::load));
        }
      });
    });

    EventBus.addHandler(InjectCarInformationEvent.TYPE, (InjectCarInformationEventHandler) event -> this.carId = event.getCarId(), true);

    EventBus.addHandler(InjectCarKitInformationEvent.TYPE, (InjectCarKitInformationEventHandler) event -> this.kitId = event.getKitId(), true);

    // if InjectCarComponentsEvent is fired, InjectCarInformationEvent and InjectCarKitInformationEvent will be ignored
    // so we will load only the injected components
    EventBus.addHandler(InjectCarComponentsEvent.TYPE, (InjectCarComponentsEventHandler) event -> this.carComponents = event.getCarComponents());

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> load(), true);
  }

  private void load() {
    if (carComponents != null) {
      load(carComponents);
    } else {
      EventBus.fireEvent(new GetCarComponentsByCarAndKitIdEvent(carId, kitId, this::load));
    }
  }

  private void load(List<CarComponentModel> carComponentsList) {
    ObservableList<CarComponentModel> items = view.getCarComponentsTable().getItems();
    items.clear();
    items.addAll(carComponentsList);
  }
}
