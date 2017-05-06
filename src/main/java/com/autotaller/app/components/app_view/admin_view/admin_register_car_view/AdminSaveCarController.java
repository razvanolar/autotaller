package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.admin_view.GetAllCarDefinedModelsEvent;
import com.autotaller.app.model.*;
import com.autotaller.app.model.utils.ModelsDTO;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogController;
import com.autotaller.app.utils.ModelFilter;
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
    ComboBox<FuelModel> getFuelCombo();
    ComboBox<CarKitCategoryModel> getCarKitCategoryCombo();
    ComboBox<CarKitModel> getCarKitCombo();
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

    view.getCarMakesCombo().valueProperty().addListener((observable, oldValue, newValue) -> populateCarModelsCombo(newValue));

    view.getCarKitCategoryCombo().valueProperty().addListener((observable, oldValue, newValue) -> populateCarKitsCombo(newValue));

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

      List<CarMakeModel> carMakes = models.getCarMakes();
      if (carMakes != null && !carMakes.isEmpty()) {
        view.getCarMakesCombo().getItems().addAll(carMakes);
        view.getCarMakesCombo().setValue(carMakes.get(0));
        populateCarModelsCombo(carMakes.get(0));
      }

      List<CarKitCategoryModel> carKitCategories = models.getCarKitCategories();
      if (carKitCategories != null && !carKitCategories.isEmpty()) {
        view.getCarKitCategoryCombo().getItems().addAll(carKitCategories);
        view.getCarKitCategoryCombo().setValue(carKitCategories.get(0));
        populateCarKitsCombo(carKitCategories.get(0));
      }

      List<FuelModel> fuels = models.getFuels();
      if (fuels != null && !fuels.isEmpty()) {
        view.getFuelCombo().getItems().addAll(fuels);
        view.getFuelCombo().setValue(fuels.get(0));
      }
    }));
  }

  private void populateCarModelsCombo(CarMakeModel carMake) {
    if (carMake != null && modelsDTO != null) {
      List<CarTypeModel> carTypes = ModelFilter.filterCarTypesByMake(modelsDTO.getCarTypes(), carMake);
      view.getCarTypesCombo().getItems().clear();
      if (!carTypes.isEmpty()) {
        view.getCarTypesCombo().getItems().addAll(carTypes);
        view.getCarTypesCombo().setValue(carTypes.get(0));
      } else {
        view.getCarTypesCombo().setValue(null);
      }
    }
  }

  private void populateCarKitsCombo(CarKitCategoryModel carKitCategory) {
    if (carKitCategory != null && modelsDTO != null) {
      List<CarKitModel> carKits = ModelFilter.filterCarKitsByCategory(modelsDTO.getCarKits(), carKitCategory);
      view.getCarKitCombo().getItems().clear();
      if (!carKits.isEmpty()) {
        view.getCarKitCombo().getItems().addAll(carKits);
        view.getCarKitCombo().setValue(carKits.get(0));
      } else {
        view.getCarKitCombo().setValue(null);
      }
    }
  }

  @Override
  public void injectActionButton(Button actionButton) {
    this.actionButton = actionButton;
  }
}
