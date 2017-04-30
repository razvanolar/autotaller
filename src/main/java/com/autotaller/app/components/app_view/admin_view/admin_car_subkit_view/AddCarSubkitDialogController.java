package com.autotaller.app.components.app_view.admin_view.admin_car_subkit_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.HideDialogEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AddCarSubkitEvent;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.model.CarSubkitModel;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogController;
import com.autotaller.app.utils.StringValidator;
import com.autotaller.app.utils.View;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * Created by razvanolar on 30.04.2017
 */
public class AddCarSubkitDialogController implements Controller<AddCarSubkitDialogController.IAddCarSubkitDialogView>, DialogController {

  public interface IAddCarSubkitDialogView extends View {
    ComboBox<CarKitModel> getCarKitsCombo();
    TextField getCarSubkitNameField();
    CheckBox getGasolineCheckBox();
    CheckBox getDieselCheckBox();
    CheckBox getGplCheckBox();
    CheckBox getElectricCheckBox();
  }

  private IAddCarSubkitDialogView view;
  private Button actionButton;

  private List<CarKitModel> carKits;

  public AddCarSubkitDialogController(List<CarKitModel> carKits) {
    this.carKits = carKits;
  }

  @Override
  public void bind(IAddCarSubkitDialogView view) {
    this.view = view;

    view.getCarKitsCombo().getItems().addAll(carKits);
    view.getCarKitsCombo().setValue(carKits.get(0));

    view.getGasolineCheckBox().setSelected(true);
    view.getDieselCheckBox().setSelected(true);
    view.getGplCheckBox().setSelected(true);
    view.getElectricCheckBox().setSelected(true);

    view.getCarSubkitNameField().setOnKeyReleased(event -> {
      if (actionButton != null)
        actionButton.setDisable(StringValidator.isNullOrEmpty(view.getCarSubkitNameField().getText()));
    });
  }

  @Override
  public void injectActionButton(Button actionButton) {
    this.actionButton = actionButton;
    actionButton.setDisable(StringValidator.isNullOrEmpty(view.getCarSubkitNameField().getText()));
    actionButton.setOnAction(event -> {
      EventBus.fireEvent(new HideDialogEvent());
      EventBus.fireEvent(new AddCarSubkitEvent(collect()));
    });
  }

  private CarSubkitModel collect() {
    return new CarSubkitModel(
            -1,
            view.getCarSubkitNameField().getText(),
            view.getCarKitsCombo().getValue(),
            view.getGasolineCheckBox().isSelected(),
            view.getDieselCheckBox().isSelected(),
            view.getGplCheckBox().isSelected(),
            view.getElectricCheckBox().isSelected()
    );
  }
}
