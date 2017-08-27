package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.HideDialogEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_model_view.AddCarModelEvent;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogController;
import com.autotaller.app.utils.StringValidator;
import com.autotaller.app.utils.View;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AddCarModelDialogController implements Controller<AddCarModelDialogController.IAddCarModelDialogView>, DialogController {

  public interface IAddCarModelDialogView extends View {
    ComboBox<CarMakeModel> getCarMakesCombo();
    TextField getNameTextField();
    DatePicker getFromDatePicker();
    DatePicker getToDatePicker();
  }

  private IAddCarModelDialogView view;
  private List<CarMakeModel> carMakes;
  private Button actionButton;

  public AddCarModelDialogController(List<CarMakeModel> carMakes) {
    this.carMakes = carMakes;
  }

  @Override
  public void bind(IAddCarModelDialogView view) {
    this.view = view;

    view.getCarMakesCombo().getItems().addAll(carMakes);
    view.getCarMakesCombo().setValue(carMakes.get(0));

    view.getNameTextField().setOnKeyReleased(event -> {
      if (actionButton != null)
        actionButton.setDisable(!isValidSelection());
    });

    view.getFromDatePicker().valueProperty().addListener((observable, oldValue, newValue) -> {
      if (actionButton != null)
        actionButton.setDisable(!isValidName() || !isValidInterval(newValue));
    });
  }

  @Override
  public void injectActionButton(Button actionButton) {
    this.actionButton = actionButton;
    actionButton.setDisable(!isValidSelection());
    actionButton.setOnAction(event -> {
      if (isValidSelection()) {
        EventBus.fireEvent(new HideDialogEvent());
        EventBus.fireEvent(new AddCarModelEvent(collect()));
      }
    });
  }

  private CarTypeModel collect() {
    return new CarTypeModel(-1, view.getCarMakesCombo().getValue(),
            view.getNameTextField().getText(),
            view.getFromDatePicker().getValue(),
            view.getToDatePicker().getValue());
  }

  private boolean isValidSelection() {
    return isValidName() && isValidInterval();
  }

  private boolean isValidName() {
    return !StringValidator.isNullOrEmpty(view.getNameTextField().getText());
  }

  private boolean isValidInterval() {
    return isValidInterval(view.getFromDatePicker().getValue());
  }

  private boolean isValidInterval(LocalDate date) {
    return date != null;
  }
}
