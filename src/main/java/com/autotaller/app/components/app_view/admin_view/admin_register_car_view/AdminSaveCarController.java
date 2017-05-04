package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogController;
import com.autotaller.app.utils.View;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Created by razvanolar on 04.05.2017
 */
public class AdminSaveCarController implements Controller<AdminSaveCarController.IAdminSaveCarView>, DialogController {

  public interface IAdminSaveCarView extends View {
    ComboBox<CarMakeModel> getCarMakesCombo();
    ComboBox<CarTypeModel> getCarTypesCombo();
    TextField getCarNameField();
    DatePicker getFromDatePicker();
    DatePicker getToDatePicker();
    Spinner<Integer> getKwSpinner();
    Spinner<Integer> getCapCilSpinner();
    Spinner<Integer> getCilindersSpinner();
    TextField getEngineField();
    Text getAddComponentsLink();
    void showComponentsView();
    void hideComponentsView();
  }

  private IAdminSaveCarView view;
  private Button actionButton;

  private List<CarMakeModel> carMakes;
  private List<CarTypeModel> carModels;


  @Override
  public void bind(IAdminSaveCarView view) {
    this.view = view;

    view.getAddComponentsLink().setOnMouseClicked(event -> {
      view.showComponentsView();
    });
  }

  @Override
  public void injectActionButton(Button actionButton) {
    this.actionButton = actionButton;
  }
}
