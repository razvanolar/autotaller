package com.autotaller.app.components.app_view.admin_view.admin_car_make_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.HideDialogEvent;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogController;
import com.autotaller.app.utils.View;
import javafx.scene.control.Button;

/**
 * Created by razvanolar on 19.04.2017
 */
public class AddCarMakeDialogController implements Controller<AddCarMakeDialogController.IAddCarMakeDialogView>, DialogController {

  public interface IAddCarMakeDialogView extends View {

  }

  @Override
  public void bind(IAddCarMakeDialogView view) {

  }

  @Override
  public void injectActionButton(Button actionButton) {
    actionButton.setOnAction(event -> {
      System.out.println("Action button");
      EventBus.fireEvent(new HideDialogEvent());
    });
  }
}
