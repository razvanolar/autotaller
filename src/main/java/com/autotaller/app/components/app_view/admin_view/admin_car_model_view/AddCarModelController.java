package com.autotaller.app.components.app_view.admin_view.admin_car_model_view;

import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AddCarModelController implements Controller<AddCarModelController.IAddCarModelView> {

  public interface IAddCarModelView extends View {
    Button getAddCarModelButton();
    ToggleButton getFilterButton();
    void showFilterPane();
    void hideFilterPane();
  }

  @Override
  public void bind(IAddCarModelView view) {

    view.getFilterButton().setOnAction(event -> {
      if (view.getFilterButton().isSelected()) {
        view.showFilterPane();
      } else {
        view.hideFilterPane();
      }
    });
  }
}
