package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.utils.SimpleDialog;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.GetAllCarDefinedModelsEvent;
import com.autotaller.app.model.*;
import com.autotaller.app.model.utils.ModelsDTO;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.ModelFilter;
import com.autotaller.app.utils.StringValidator;
import com.autotaller.app.utils.View;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 04.05.2017
 */
public class AdminSaveCarController implements Controller<AdminSaveCarController.IAdminSaveCarView> {

  public interface IAdminSaveCarView extends View {
    ComboBox<CarMakeModel> getCarMakesCombo();
    ComboBox<CarTypeModel> getCarTypesCombo();
    ComboBox<FuelModel> getFuelCombo();
    ComboBox<CarKitCategoryModel> getCarKitCategoryCombo();
    ComboBox<CarKitModel> getCarKitCombo();
    ComboBox<CarSubkitModel> getCarSubkitCombo();
    TextField getCarNameField();
    DatePicker getFromDatePicker();
    DatePicker getToDatePicker();
    Spinner<Integer> getKwSpinner();
    Spinner<Integer> getCapCilSpinner();
    Spinner<Integer> getCilindersSpinner();
    TextField getEngineField();
    Text getAddComponentsLink();
    TableView<CarComponentModel> getComponentsTable();
    TextField getComponentNameTextField();
    TextField getComponentCodeTextField();
    TextField getComponentStockTextField();
    TextArea getComponentDescriptionTextArea();
    TextField getComponentImageTextField();
    Button getComponentImageAddButton();
    Button getComponentAddButton();
    void showComponentsView();
    void hideComponentsView();
  }

  private IAdminSaveCarView view;

  private ModelsDTO modelsDTO;

  private CarKitCategoryModel allCarKitCategory = new CarKitCategoryModel(-1, "--Toate--");
  private CarKitModel allCarKit = new CarKitModel(-1, "--Toate--", allCarKitCategory);

  @Override
  public void bind(IAdminSaveCarView view) {
    this.view = view;

    view.getAddComponentsLink().setOnMouseClicked(event -> {
      view.showComponentsView();
    });

    // Car Make combo selection
    view.getCarMakesCombo().valueProperty().addListener((observable, oldValue, newValue) -> populateCarModelsCombo(newValue));

    // Car Kit Category selection
    view.getCarKitCategoryCombo().valueProperty().addListener((observable, oldValue, newValue) -> populateCarKitsCombo(newValue));

    // Car Kit selection
    view.getCarKitCombo().valueProperty().addListener((observable, oldValue, newValue) -> populateCarSubkitsCombo(newValue));

    // Car Fuel selection
    view.getFuelCombo().valueProperty().addListener((observable, oldValue, newValue) -> fuelSelectionChanged(newValue));

    // Add Car Component Selection
    view.getComponentAddButton().setOnAction(event -> {
      CarComponentModel carComponent = collectComponent();
      if (carComponent != null) {
        view.getComponentsTable().getItems().add(carComponent);
      } else {
        EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Unele campuri obligatorii nu sunt completate")));
      }
    });

    EventBus.fireEvent(new GetAllCarDefinedModelsEvent(models -> {
      modelsDTO = models;

      List<CarMakeModel> carMakes = models.getCarMakes();
      if (carMakes != null && !carMakes.isEmpty()) {
        view.getCarMakesCombo().getItems().addAll(carMakes);
        view.getCarMakesCombo().setValue(carMakes.get(0));
        populateCarModelsCombo(carMakes.get(0));
      }

      List<CarKitCategoryModel> carKitCategories = models.getCarKitCategories();
      if (carKitCategories == null) {
        carKitCategories = new ArrayList<>();
      }
      carKitCategories.add(0, allCarKitCategory);
      view.getCarKitCategoryCombo().getItems().addAll(carKitCategories);
      view.getCarKitCategoryCombo().setValue(carKitCategories.get(0));
      populateCarKitsCombo(carKitCategories.get(0));

      List<FuelModel> fuels = models.getFuels();
      if (fuels != null && !fuels.isEmpty()) {
        view.getFuelCombo().getItems().addAll(fuels);
        view.getFuelCombo().setValue(fuels.get(0));
        fuelSelectionChanged(fuels.get(0));
      }
    }));
  }

  private CarComponentModel collectComponent() {
    CarSubkitModel carSubkit = view.getCarSubkitCombo().getValue();
    String componentName = view.getComponentNameTextField().getText();
    String componentCode = view.getComponentCodeTextField().getText();
    String componentStock = view.getComponentStockTextField().getText();
    if (carSubkit == null || StringValidator.isNullOrEmpty(componentName) || StringValidator.isNullOrEmpty(componentCode)
            || StringValidator.isNullOrEmpty(componentStock)) {
      return null;
    }
    return new CarComponentModel(-1, -1, carSubkit.getId(), componentName, componentCode, componentStock);
  }

  private void populateCarModelsCombo(CarMakeModel carMake) {
    if (carMake != null && modelsDTO != null) {
      List<CarTypeModel> carTypes = modelsDTO.getCarTypesByMake(carMake);
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
      List<CarKitModel> carKits;
      if (carKitCategory.getId() == -1) {
        carKits = new ArrayList<>(modelsDTO.getCarKits());
        carKits.add(0, allCarKit);
      } else {
        carKits = modelsDTO.getCarKitByCategory(carKitCategory);
      }
      view.getCarKitCombo().getItems().clear();
      if (!carKits.isEmpty()) {
        view.getCarKitCombo().getItems().addAll(carKits);
        view.getCarKitCombo().setValue(carKits.get(0));
      } else {
        view.getCarKitCombo().setValue(null);
      }
    }
  }

  private void populateCarSubkitsCombo(CarKitModel carKit) {
    if (carKit != null && modelsDTO != null) {
      List<CarSubkitModel> carSubkits;
      if (carKit.getId() == -1) {
        carSubkits = new ArrayList<>(modelsDTO.getCarSubkits());
      } else {
        carSubkits = ModelFilter.filterCarSubkitsByKit(carKit);
      }
      view.getCarSubkitCombo().getItems().clear();
      if (!carSubkits.isEmpty()) {
        view.getCarSubkitCombo().getItems().addAll(carSubkits);
        view.getCarSubkitCombo().setValue(carSubkits.get(0));
      } else {
        view.getCarSubkitCombo().setValue(null);
      }
    }
  }

  private void fuelSelectionChanged(FuelModel fuel) {

  }
}
