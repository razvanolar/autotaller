package com.autotaller.app.components.app_view.admin_view.admin_car_kit_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.HideDialogEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AddCarKitEvent;
import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogController;
import com.autotaller.app.utils.StringValidator;
import com.autotaller.app.utils.View;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * Created by razvanolar on 30.04.2017
 */
public class AddCarKitDialogController implements Controller<AddCarKitDialogController.IAddCarKitDialogView>, DialogController {

  public interface IAddCarKitDialogView extends View {
    ComboBox<CarKitCategoryModel> getCarKitCategoriesCombo();
    TextField getCarKitNameField();
  }

  private IAddCarKitDialogView view;
  private Button actionButton;
  private List<CarKitCategoryModel> carKitCategories;

  public AddCarKitDialogController(List<CarKitCategoryModel> carKitCategories) {
    this.carKitCategories = carKitCategories;
  }

  @Override
  public void bind(IAddCarKitDialogView view) {
    this.view = view;

    view.getCarKitNameField().setOnKeyReleased(event -> {
      if (actionButton != null)
        actionButton.setDisable(StringValidator.isNullOrEmpty(view.getCarKitNameField().getText()));
    });

    ObservableList<CarKitCategoryModel> items = view.getCarKitCategoriesCombo().getItems();
    items.clear();
    items.addAll(carKitCategories);
    view.getCarKitCategoriesCombo().setValue(carKitCategories.get(0));
  }

  @Override
  public void injectActionButton(Button actionButton) {
    this.actionButton = actionButton;
    this.actionButton.setDisable(StringValidator.isNullOrEmpty(view.getCarKitNameField().getText()));
    actionButton.setOnAction(event -> {
      EventBus.fireEvent(new HideDialogEvent());
      EventBus.fireEvent(new AddCarKitEvent(collect()));
    });
  }

  private CarKitModel collect() {
    return new CarKitModel(-1, view.getCarKitNameField().getText(), view.getCarKitCategoriesCombo().getValue());
  }
}
