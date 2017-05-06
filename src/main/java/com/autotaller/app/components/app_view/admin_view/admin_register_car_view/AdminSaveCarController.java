package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.admin_view.GetAllCarDefinedModelsEvent;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarSubkitModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.model.utils.ModelsDTO;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogController;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
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
    void updateFilterPane(List<AdminSaveCarController.ComponentRowWrapper> componentRowWrappers);
  }

  public class ComponentRowWrapper {

    private CarSubkitModel subkit;

    private TextField nameField;
    private TextField codeField;
    private TextField stockField;

    public ComponentRowWrapper(CarSubkitModel subkit, TextField nameField, TextField codeField, TextField stockField) {
      this.subkit = subkit;
      this.nameField = nameField;
      this.codeField = codeField;
      this.stockField = stockField;
    }

    public CarSubkitModel getSubkit() {
      return subkit;
    }

    public TextField getNameField() {
      return nameField;
    }

    public TextField getCodeField() {
      return codeField;
    }

    public TextField getStockField() {
      return stockField;
    }
  }

  private IAdminSaveCarView view;
  private Button actionButton;

  private ModelsDTO modelsDTO;
  private List<ComponentRowWrapper> componentRowWrappers;

  @Override
  public void bind(IAdminSaveCarView view) {
    this.view = view;

    view.getAddComponentsLink().setOnMouseClicked(event -> {
      view.showComponentsView();
    });

    EventBus.fireEvent(new GetAllCarDefinedModelsEvent(models -> {
      modelsDTO = models;
      if (models.getCarSubkits() != null) {
        componentRowWrappers = new ArrayList<>();
        for (CarSubkitModel subKit : models.getCarSubkits()) {
          componentRowWrappers.add(new ComponentRowWrapper(
                  subKit,
                  NodeProvider.createTextField(),
                  NodeProvider.createTextField(),
                  NodeProvider.createTextField()
          ));
        }
        view.updateFilterPane(componentRowWrappers);
      }
    }));
  }

  @Override
  public void injectActionButton(Button actionButton) {
    this.actionButton = actionButton;
  }
}
