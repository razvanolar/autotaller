package com.autotaller.app.components.app_view.admin_view.admin_car_model_view;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogController;
import com.autotaller.app.utils.View;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.List;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AddCarModelDialogController implements Controller<AddCarModelDialogController.IAddCarModelDialogView>, DialogController {

  public interface IAddCarModelDialogView extends View {
    ComboBox<CarMakeModel> getCarMakesCombo();
  }

  private List<CarMakeModel> carMakes;

  public AddCarModelDialogController(List<CarMakeModel> carMakes) {
    this.carMakes = carMakes;
  }

  @Override
  public void bind(IAddCarModelDialogView view) {
    view.getCarMakesCombo().getItems().addAll(carMakes);
    view.getCarMakesCombo().setValue(carMakes.get(0));
  }

  @Override
  public void injectActionButton(Button actionButton) {

  }
}
