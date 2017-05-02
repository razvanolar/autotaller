package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_make_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEventHandler;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.AdminToolbarView;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogComponentType;
import com.autotaller.app.utils.factories.DialogFactory;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminCarMakeController implements Controller<AdminCarMakeController.ICarsAdminView> {

  public interface ICarsAdminView extends AdminToolbarView {
    void addNode(CarMakeModel model);
    void clearNodes();
  }

  @Override
  public void bind(ICarsAdminView view) {
    view.getAddButton().setOnAction(event -> EventBus.fireEvent(new ShowDialogEvent(DialogFactory.createDialog(DialogComponentType.ADD_CAR_MAKE_DIALOG))));

    EventBus.addHandler(AdminLoadCarMakesEvent.TYPE, (AdminLoadCarMakesEventHandler) event -> {
      view.clearNodes();
      for (CarMakeModel car : event.getCarMakeModels()) {
        view.addNode(car);
      }
    });
  }
}
