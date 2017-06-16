package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.utils.ImageGalleryPane;
import com.autotaller.app.components.utils.SimpleDialog;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.GetAllCarDefinedModelsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_view.AddCarEvent;
import com.autotaller.app.events.view_stack.BackToPreviousViewEvent;
import com.autotaller.app.model.*;
import com.autotaller.app.model.utils.CarDefinedModelsDTO;
import com.autotaller.app.utils.CarWheelSideType;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.StringValidator;
import com.autotaller.app.utils.View;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 04.05.2017
 */
public class AdminSaveCarController implements Controller<AdminSaveCarController.IAdminSaveCarView> {

  public interface IAdminSaveCarView extends View {
    TableView<CarMakeModel> getCarMakesTable();
    TableView<CarTypeModel> getCarTypesTable();
    Region getSaveCarForm();
    Button getContinueButton();
    Button getBackButton();
    void setActiveNode(Region node);
    Region getActiveNode();
    Text getPathText();
    Text getCarMakeText();
    Text getCarTypeText();
    TextField getCarNameField();
    ComboBox<CarBodyTypeModel> getCarBodyTypeCombo();
    DatePicker getProducedFromPicker();
    DatePicker getProducedToPicker();
    DatePicker getProductionYearPicker();
    TextField getCarKmField();
    Spinner<Integer> getCarKwSpinner();
    Spinner<Integer> getCarCapacitySpinner();
    Spinner<Integer> getCarCylindersSpinner();
    TextField getEnginesTextField();
    ComboBox<FuelModel> getCarFuelCombo();
    TextField getCarColorCodeField();
    TextField getCarPriceField();
    RadioButton getCarLeftWheelRadio();
    TextArea getCarDescriptionTextArea();
    ImageGalleryPane getImageGalleryPane();
  }

  private IAdminSaveCarView view;
  private CarDefinedModelsDTO carSystemModels;

  private CarMakeModel selectedCarMake;
  private CarTypeModel selectedCarType;

  @Override
  public void bind(IAdminSaveCarView view) {
    this.view = view;

    view.getContinueButton().setOnAction(event -> {
      Region activeNode = view.getActiveNode();
      if (activeNode == view.getCarMakesTable()) {
        continueAfterCarMakeSelection();
      } else if (activeNode == view.getCarTypesTable()) {
        continueAfterCarTypeSelection();
      } else if (activeNode == view.getSaveCarForm()) {
        continueAfterSaveCarForm();
      }
    });

    view.getBackButton().setOnAction(event -> {
      Node activeNode = view.getActiveNode();
      if (activeNode == view.getCarMakesTable()) {
        EventBus.fireEvent(new BackToPreviousViewEvent());
      } else if (activeNode == view.getCarTypesTable()) {
        backFromCarTypeView();
      } else if (activeNode == view.getSaveCarForm()) {
        backFromSaveCarFormView();
      }
    });

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> EventBus.fireEvent(new GetAllCarDefinedModelsEvent(carModelsDTO -> {
      this.carSystemModels = carModelsDTO;
      ObservableList<CarMakeModel> items = view.getCarMakesTable().getItems();
      items.clear();
      items.addAll(carSystemModels.getCarMakes());
    })), true);
  }

  private void continueAfterCarMakeSelection() {
    CarMakeModel selectedMake = view.getCarMakesTable().getSelectionModel().getSelectedItem();
    if (selectedMake != null) {
      List<CarTypeModel> carTypes = carSystemModels.getCarTypesByMake(selectedMake);
      if (carTypes != null && !carTypes.isEmpty()) {
        selectedCarMake = selectedMake;
        ObservableList<CarTypeModel> items = view.getCarTypesTable().getItems();
        items.clear();
        items.addAll(carTypes);
        view.setActiveNode(view.getCarTypesTable());
        setPathInfo();
      } else {
        EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Nu exista" +
                "nici un model definit pentru marca " + selectedMake.getName())));
      }
    } else {
      EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Selecteaza" +
              " o marca pentru a putea continua adaugarea")));
    }
  }

  private void continueAfterCarTypeSelection() {
    CarTypeModel selectedType = view.getCarTypesTable().getSelectionModel().getSelectedItem();
    if (selectedType != null) {
      selectedCarType = selectedType;
      view.setActiveNode(view.getSaveCarForm());
      setPathInfo();
      view.getCarMakeText().setText(selectedCarMake.getName());
      view.getCarTypeText().setText(selectedCarType.getName());
      view.getProducedFromPicker().setValue(selectedCarType.getFrom());
      view.getProducedToPicker().setValue(selectedCarType.getTo());
      ObservableList<FuelModel> fuels = view.getCarFuelCombo().getItems();
      ObservableList<CarBodyTypeModel> bodyTypes = view.getCarBodyTypeCombo().getItems();
      List<FuelModel> fuelsList = carSystemModels.getFuels();
      List<CarBodyTypeModel> bodyTypesList = carSystemModels.getBodyTypes();
      if (fuels.isEmpty() && fuelsList != null && !fuelsList.isEmpty()) {
        fuels.addAll(fuelsList);
        view.getCarFuelCombo().setValue(fuelsList.get(0));
      }
      if (bodyTypes.isEmpty() && bodyTypesList != null && !bodyTypesList.isEmpty()) {
        bodyTypes.addAll(bodyTypesList);
        view.getCarBodyTypeCombo().setValue(bodyTypesList.get(0));
      }
    } else {
      EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Selecteaza" +
              " un model pentru a putea continua adaugarea")));
    }
  }

  private void continueAfterSaveCarForm() {
    CarModel car = collectCar();
    if (car == null) {
      EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Nu toate campurile obligatorii sunt completate")));
      return;
    }

    EventBus.fireEvent(new AddCarEvent(car, view.getImageGalleryPane().getImages()));
  }

  private void backFromCarTypeView() {
    selectedCarMake = null;
    selectedCarType = null;
    view.setActiveNode(view.getCarMakesTable());
    setPathInfo();
  }

  private void backFromSaveCarFormView() {
    selectedCarType = null;
    view.setActiveNode(view.getCarTypesTable());
    setPathInfo();
  }

  private void setPathInfo() {
    String text = "> ";
    if (selectedCarMake != null) {
      text += selectedCarMake.getName();
    }
    if (selectedCarMake != null && selectedCarType != null) {
      text += " > " + selectedCarType.getName();
    }
    view.getPathText().setText(text);
  }

  private CarModel collectCar() {
    String carName = view.getCarNameField().getText();
    CarBodyTypeModel bodyType = view.getCarBodyTypeCombo().getValue();
    LocalDate prodYear = view.getProductionYearPicker().getValue();
    LocalDate prodFrom = view.getProducedFromPicker().getValue();
    LocalDate prodTo = view.getProducedToPicker().getValue();
    Integer carKW = view.getCarKwSpinner().getValue();
    Integer carCapacity = view.getCarCapacitySpinner().getValue();
    Integer carCylinders = view.getCarCylindersSpinner().getValue();
    FuelModel carFuel = view.getCarFuelCombo().getValue();
    String priceText = view.getCarPriceField().getText();
    if (selectedCarMake == null || selectedCarType == null || StringValidator.isNullOrEmpty(carName) ||
            prodYear == null || prodFrom == null || prodTo == null ||
            !StringValidator.isPositiveInteger(view.getCarKmField().getText()) || carKW == null || carCapacity == null ||
            carCylinders == null || carFuel == null || bodyType == null) {
      return null;
    }

    String enginesText = view.getEnginesTextField().getText();
    List<String> engines = null;
    if (!StringValidator.isNullOrEmpty(enginesText)) {
      String[] values = enginesText.trim().split(",");
      engines = new ArrayList<>();
      for (String s : values) {
        if (!StringValidator.isNullOrEmpty(s))
          engines.add(s.trim());
      }
    }

    int price = StringValidator.isPositiveInteger(priceText) ? Integer.parseInt(priceText) : 0;
    return new CarModel(-1, selectedCarType, carName, bodyType, prodFrom, prodTo, prodYear,
            Integer.parseInt(view.getCarKmField().getText()), carKW, carCapacity, carCylinders, engines,
            carFuel, view.getCarColorCodeField().getText().trim(),
            price, view.getCarLeftWheelRadio().isSelected() ? CarWheelSideType.LEFT : CarWheelSideType.RIGHT,
            view.getCarDescriptionTextArea().getText());
  }
}
