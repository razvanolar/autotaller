package com.autotaller.app.components.app_view.admin_view.admin_car_model_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_model_view.AdminLoadCarModelsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_model_view.AdminLoadCarModelsEventHandler;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogComponentType;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.DialogFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.List;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AdminCarModelController implements Controller<AdminCarModelController.IAdminCarModelView> {

  public interface IAdminCarModelView extends View {
    Button getAddCarModelButton();
    ToggleButton getFilterButton();
    TableView<CarTypeModel> getCarModelTable();
    ComboBox<CarMakeModel> getCarMakeCombo();
    TextField getCarModelNameTextField();
    DatePicker getFromDatePicker();
    DatePicker getToDatePicker();
    TextField getEngineTextField();
    void showFilterPane();
    void hideFilterPane();
  }

  private IAdminCarModelView view;

  private CarMakeModel allCarMakesItem = new CarMakeModel(-1, "--Toate--");
  private List<CarMakeModel> carMakes;
  private List<CarTypeModel> carModels;

  @Override
  public void bind(IAdminCarModelView view) {
    this.view = view;

    view.getAddCarModelButton().setOnAction(event -> {
      AddCarModelDialogController controller = new AddCarModelDialogController(carMakes);
      AddCarModelDialogView dialogView = new AddCarModelDialogView();
      Component component = new Component(controller, dialogView);
      EventBus.fireEvent(new ShowDialogEvent(DialogFactory.createDialog(DialogComponentType.ADD_CAR_MODEL_DIALOG, component)));
    });

    view.showFilterPane();
    view.getFilterButton().setSelected(true);
    view.getFilterButton().setOnAction(event -> {
      if (view.getFilterButton().isSelected()) {
        view.showFilterPane();
      } else {
        view.hideFilterPane();
      }
    });

    EventBus.addHandler(AdminLoadCarMakesEvent.TYPE, (AdminLoadCarMakesEventHandler) event -> {
      carMakes = event.getCarMakeModels();
      updateFilterPane();
    });

    EventBus.addHandler(AdminLoadCarModelsEvent.TYPE, (AdminLoadCarModelsEventHandler) event -> {
      carModels = event.getCarModels();
      ObservableList<CarTypeModel> items = view.getCarModelTable().getItems();
      items.clear();
      items.addAll(carModels);
      updateFilterPane();
    });
  }

  private void updateFilterPane() {
    CarMakeModel selectedCarMake = view.getCarMakeCombo().getValue();
    ObservableList<CarMakeModel> carMakeStore = view.getCarMakeCombo().getItems();
    carMakeStore.clear();
    carMakeStore.add(allCarMakesItem);
    carMakeStore.addAll(carMakes);
    view.getCarMakeCombo().setValue(selectedCarMake != null ? selectedCarMake : allCarMakesItem);
  }
}
