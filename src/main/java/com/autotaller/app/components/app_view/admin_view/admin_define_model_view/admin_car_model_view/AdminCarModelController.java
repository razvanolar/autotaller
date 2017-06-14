package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.utils.IDefineModelController;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarMakesEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarModelsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_model_view.AdminLoadCarModelsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_model_view.AdminLoadCarModelsEventHandler;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.factories.DialogFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.List;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AdminCarModelController implements Controller<AdminCarModelController.IAdminCarModelView>, IDefineModelController {

  public interface IAdminCarModelView extends View {
    TableView<CarTypeModel> getCarModelTable();
  }

  private IAdminCarModelView view;

  private List<CarMakeModel> carMakes;
  private List<CarTypeModel> carModels;

  @Override
  public void bind(IAdminCarModelView view) {
    this.view = view;

    EventBus.addHandler(AdminLoadCarMakesEvent.TYPE, (AdminLoadCarMakesEventHandler) event -> loadCarMakes(event.getCarMakeModels()));

    EventBus.addHandler(AdminLoadCarModelsEvent.TYPE, (AdminLoadCarModelsEventHandler) event -> loadCarTypes(event.getCarModels()));
  }

  private void loadCarTypes() {
    EventBus.fireEvent(new GetCarModelsEvent(this::loadCarTypes));
  }

  private void loadCarTypes(List<CarTypeModel> carTypes) {
    this.carModels = carTypes;
    ObservableList<CarTypeModel> items = view.getCarModelTable().getItems();
    items.clear();
    items.addAll(carTypes);
  }

  private void loadCarMakes() {
    EventBus.fireEvent(new GetCarMakesEvent(this::loadCarMakes));
  }

  private void loadCarMakes(List<CarMakeModel> carMakes) {
    this.carMakes = carMakes;
  }

  @Override
  public void loadIfEmpty() {
    ObservableList<CarTypeModel> items = view.getCarModelTable().getItems();
    if (items.isEmpty())
      loadCarTypes();
    if (carMakes == null || carMakes.isEmpty())
      loadCarMakes();
  }

  @Override
  public void addEntity() {
    AddCarModelDialogController controller = new AddCarModelDialogController(carMakes);
    AddCarModelDialogView dialogView = new AddCarModelDialogView();
    Component component = new Component(controller, dialogView);
    EventBus.fireEvent(new ShowDialogEvent(DialogFactory.createDialog(DialogComponentType.ADD_CAR_MODEL_DIALOG, component)));
  }

  @Override
  public View getView() {
    return view;
  }
}
