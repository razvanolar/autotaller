package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_make_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.utils.IDefineModelController;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarMakesEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEventHandler;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.AdminToolbarView;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogComponentType;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.DialogFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminCarMakeController implements Controller<AdminCarMakeController.ICarsAdminView>, IDefineModelController {

  public interface ICarsAdminView extends View {
    TableView<CarMakeModel> getCarMakeTable();
  }

  private ICarsAdminView view;

  @Override
  public void bind(ICarsAdminView view) {
    this.view = view;

    EventBus.addHandler(AdminLoadCarMakesEvent.TYPE, (AdminLoadCarMakesEventHandler) event -> load(event.getCarMakeModels()));
  }

  private void load() {
    EventBus.fireEvent(new GetCarMakesEvent(this::load));
  }

  private void load(List<CarMakeModel> carMakes) {
    ObservableList<CarMakeModel> items = view.getCarMakeTable().getItems();
    items.clear();
    items.addAll(carMakes);
  }

  @Override
  public void loadIfEmpty() {
    ObservableList<CarMakeModel> items = view.getCarMakeTable().getItems();
    if (items.isEmpty())
      load();
  }

  @Override
  public void addEntity() {
    EventBus.fireEvent(new ShowDialogEvent(DialogFactory.createDialog(DialogComponentType.ADD_CAR_MAKE_DIALOG)));
  }

  @Override
  public View getView() {
    return view;
  }
}
