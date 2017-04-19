package com.autotaller.app.components.app_view.admin_view.admin_car_make_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEventHandler;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogComponentType;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.DialogFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminCarMakeController implements Controller<AdminCarMakeController.ICarsAdminView> {

  public interface ICarsAdminView extends View {
    void addNode(CarMakeModel model);
    Button getAddCarMakeButton();
    ToggleButton getFilterCarMakeButton();
    void showFilterCarMakePane();
    void hideAdditionalPanel();
  }

  @Override
  public void bind(ICarsAdminView view) {

    view.getAddCarMakeButton().setOnAction(event -> EventBus.fireEvent(new ShowDialogEvent(DialogFactory.createDialog(DialogComponentType.ADD_CAR_MAKE_DIALOG))));

    view.getFilterCarMakeButton().setOnAction(event -> {
      if (event.getSource() == view.getFilterCarMakeButton() && view.getFilterCarMakeButton().isSelected()) {
        view.showFilterCarMakePane();
      } else {
        view.hideAdditionalPanel();
      }
    });

    EventBus.addHandler(AdminLoadCarMakesEvent.TYPE, (AdminLoadCarMakesEventHandler) event -> {
      for (CarMakeModel car : event.getCarMakeModels()) {
        view.addNode(car);
      }
    });
  }
}
