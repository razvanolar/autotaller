package com.autotaller.app.components.app_view.admin_view.admin_car_make_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.HideDialogEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AddCarMakeEvent;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogController;
import com.autotaller.app.utils.StringValidator;
import com.autotaller.app.utils.View;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Created by razvanolar on 19.04.2017
 */
public class AddCarMakeDialogController implements Controller<AddCarMakeDialogController.IAddCarMakeDialogView>, DialogController {

  public interface IAddCarMakeDialogView extends View {
    TextField getCarMakeNameField();
  }

  private IAddCarMakeDialogView view;
  private Button actionButton;

  @Override
  public void bind(IAddCarMakeDialogView view) {
    this.view = view;
    view.getCarMakeNameField().textProperty().addListener((observable, oldValue, newValue) -> {
      if (actionButton != null) {
        actionButton.setDisable(StringValidator.isNullOrEmpty(newValue));
      }
    });
  }

  @Override
  public void injectActionButton(Button actionButton) {
    this.actionButton = actionButton;
    actionButton.setDisable(StringValidator.isNullOrEmpty(view.getCarMakeNameField().getText()));
    actionButton.setOnAction(event -> {
      EventBus.fireEvent(new HideDialogEvent());
      EventBus.fireEvent(new AddCarMakeEvent(view.getCarMakeNameField().getText()));
    });
  }
}
