package com.autotaller.app.components.app_view.cars_view.search_car_make_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.utils.ChipSet;
import com.autotaller.app.components.utils.FilterDialog;
import com.autotaller.app.components.utils.SimpleDialog;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.GetAllCarDefinedModelsEvent;
import com.autotaller.app.events.app_view.search_views.InjectCarTypesEvent;
import com.autotaller.app.events.app_view.search_views.ShowFilterDialogEvent;
import com.autotaller.app.events.app_view.search_views.ShowFilterDialogEventHandler;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.model.utils.CarDefinedModelsDTO;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

import java.util.List;

/**
 * Created by razvanolar on 02.06.2017
 */
public class SearchCarMakeController implements Controller<SearchCarMakeController.ISearchCarMakeView> {

  public interface ISearchCarMakeView extends View {
    void addChipSet(ChipSet chipSet);
    TableView<CarMakeModel> getCarMakeTable();
    Button getContinueButton();
  }

  private ISearchCarMakeView view;
  private CarDefinedModelsDTO definedModels;

  @Override
  public void bind(ISearchCarMakeView view) {
    this.view = view;

//    view.addChipSet(new ChipSet("test"));

    view.getContinueButton().setOnAction(event -> {
      CarMakeModel selectedMake = view.getCarMakeTable().getSelectionModel().getSelectedItem();
      if (selectedMake == null) {
        return;
      }
      List<CarTypeModel> carTypes = definedModels.getCarTypesByMake(selectedMake);
      if (carTypes != null && !carTypes.isEmpty()) {
        Component component = ComponentFactory.createComponent(ComponentType.SEARCH_CAR_TYPE_VIEW);
        if (component != null) {
          String title = ComponentType.SEARCH_CAR_TYPE_VIEW.getTitle();
          EventBus.fireEvent(new AddViewToStackEvent(component.getView(), title + " (Marca: " + selectedMake.getName() + ")"));
          EventBus.fireEvent(new InjectCarTypesEvent(carTypes));
          EventBus.fireEvent(new BindLastViewEvent());
        }
      } else {
        EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Nu exista nici un model definit pentru " + selectedMake.getName())));
      }
    });

    EventBus.addHandler(ShowFilterDialogEvent.TYPE, (ShowFilterDialogEventHandler) event -> {
      CarMakeFilterView carMakeFilterView = new CarMakeFilterView();
      EventBus.fireEvent(new ShowDialogEvent(new FilterDialog((Region) carMakeFilterView.asNode())));
    }, true);

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> load(), true);
  }

  private void load() {
    EventBus.fireEvent(new GetAllCarDefinedModelsEvent(carModelsDTO -> {
      this.definedModels = carModelsDTO;
      ObservableList<CarMakeModel> items = view.getCarMakeTable().getItems();
      items.clear();
      items.addAll(carModelsDTO.getCarMakes());
    }));
  }
}
