package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.utils.SimpleDialog;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.GetAllCarDefinedModelsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_view.AddCarEvent;
import com.autotaller.app.model.*;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.filters.ModelFilter;
import com.autotaller.app.utils.StringValidator;
import com.autotaller.app.utils.View;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 04.05.2017
 */
public class AdminSaveCarController implements Controller<AdminSaveCarController.IAdminSaveCarView> {

  public interface IAdminSaveCarView extends View {
    Button getSaveCarButton();
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
    TextArea getCarDescriptionTextArea();
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

  private SystemModelsDTO systemModelsDTO;

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

    setSpinnerValueListener(view.getKwSpinner());
    setSpinnerValueListener(view.getCapCilSpinner());
    setSpinnerValueListener(view.getCilindersSpinner());

    // Add Car Component Selection
    view.getComponentAddButton().setOnAction(event -> {
      CarComponentModel carComponent = collectComponent();
      if (carComponent != null) {
        view.getComponentsTable().getItems().add(carComponent);
      } else {
        EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Unele campuri obligatorii nu sunt completate")));
      }
    });

    // Save Car selection
    view.getSaveCarButton().setOnAction(event -> {
      CarModel car = collectCar();
      if (car != null) {
        ArrayList<CarComponentModel> components = new ArrayList<>(view.getComponentsTable().getItems());
        EventBus.fireEvent(new AddCarEvent(car, components));
      } else {
        EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Unele campuri obligatorii nu sunt completate")));
      }
    });

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> EventBus.fireEvent(new GetAllCarDefinedModelsEvent(models -> {
      systemModelsDTO = models;

      List<CarMakeModel> carMakes = models.getCarMakes();
      if (carMakes != null && !carMakes.isEmpty()) {
        view.getCarMakesCombo().getItems().addAll(carMakes);
        view.getCarMakesCombo().setValue(carMakes.get(0));
        populateCarModelsCombo(carMakes.get(0));
      }

      List<CarKitCategoryModel> carKitCategories = new ArrayList<>(models.getCarKitCategories());
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
    })), true);
  }

  private CarModel collectCar() {
    CarTypeModel carType = view.getCarTypesCombo().getValue();
    String carName = view.getCarNameField().getText();
    LocalDate carFrom = view.getFromDatePicker().getValue();
    LocalDate carTo = view.getToDatePicker().getValue();
    Integer carKW = view.getKwSpinner().getValue();
    Integer carCapacity = view.getCapCilSpinner().getValue();
    Integer carCilinders = view.getCilindersSpinner().getValue();
    String carEngineCode = view.getEngineField().getText();
    FuelModel carFuel = view.getFuelCombo().getValue();

    if (carType == null || carFrom == null || carTo == null || carKW == null || carCapacity == null || carCilinders == null
            || carFuel == null || StringValidator.isNullOrEmpty(carName) || StringValidator.isNullOrEmpty(carEngineCode)) {
      return null;
    }

    String[] split = carEngineCode.trim().split(",");
    List<String> engines = new ArrayList<>(split.length);
    for (String s : split)
      engines.add(s.trim());
    return new CarModel(-1, carType, carName, carFrom, carTo, carKW, carCapacity, carCilinders, engines, carFuel,
            view.getCarDescriptionTextArea().getText());
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
    return new CarComponentModel(-1, -1, carSubkit.getId(), componentName, componentCode, componentStock, view.getComponentDescriptionTextArea().getText());
  }

  private void populateCarModelsCombo(CarMakeModel carMake) {
    if (carMake != null && systemModelsDTO != null) {
      List<CarTypeModel> carTypes = systemModelsDTO.getCarTypesByMake(carMake);
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
    if (carKitCategory != null && systemModelsDTO != null) {
      List<CarKitModel> carKits;
      if (carKitCategory.getId() == -1) {
        carKits = new ArrayList<>(systemModelsDTO.getCarKits());
        carKits.add(0, allCarKit);
      } else {
        carKits = systemModelsDTO.getCarKitByCategory(carKitCategory);
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
    if (carKit != null && systemModelsDTO != null) {
      List<CarSubkitModel> carSubkits;
      if (carKit.getId() == -1) {
        carSubkits = new ArrayList<>(systemModelsDTO.getCarSubkits());
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

  private void setSpinnerValueListener(Spinner<Integer> spinner) {
    spinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
      if (StringValidator.isPositiveInteger(newValue)) {
        spinner.getValueFactory().setValue(Integer.parseInt(newValue));
      }
    });
  }
}
