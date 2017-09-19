package com.autotaller.app.components.app_view.admin_view.admin_register_car_view.utils;

import com.autotaller.app.model.CarBodyTypeModel;
import com.autotaller.app.model.FuelModel;
import com.autotaller.app.utils.CarWheelSideType;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CarFormView {

  GridPane carFormPane;

  private Text carMakeText;
  private Text carTypeText;
  private TextField carNameField;
  private ComboBox<CarBodyTypeModel> carBodyTypeCombo;
  private DatePicker producedFromPicker;
  private DatePicker producedToPicker;
  private DatePicker productionYearPicker;
  private TextField carParkNumberField;
  private TextField carKmField;
  private Spinner<Integer> carKwSpinner;
  private Spinner<Integer> carCapacitySpinner;
  private Spinner<Integer> carCylindersSpinner;
  private TextField enginesTextField;
  private ComboBox<FuelModel> carFuelCombo;
  private TextField carColorCodeField;
  private TextField carPriceField;
  private RadioButton carLeftWheelRadio;

  public CarFormView() {
    init();
  }

  private void init() {
    int width = NodeProvider.DEFAULT_FIELD_WIDTH + 80;
    carMakeText = NodeProvider.createFormTextLabel("");
    carTypeText = NodeProvider.createFormTextLabel("");
    carNameField = NodeProvider.createTextField(width);
    carBodyTypeCombo = NodeProvider.createCarBodyTypeCombo(width);
    productionYearPicker = NodeProvider.createDatePicker(width);
    producedFromPicker = NodeProvider.createDatePicker(width);
    producedToPicker = NodeProvider.createDatePicker(width);
    carParkNumberField = NodeProvider.createTextField(width);
    carKmField = NodeProvider.createTextField(width);
    carKwSpinner = NodeProvider.createSpinner(width,0, 1000, 0, 1);
    carCapacitySpinner = NodeProvider.createSpinner(width,0, 10000, 0, 1);
    carCylindersSpinner = NodeProvider.createSpinner(width,0, 100, 0, 1);
    enginesTextField = NodeProvider.createTextField(width);
    carFuelCombo = NodeProvider.createFuelCombo(width);
    carColorCodeField = NodeProvider.createTextField(width);
    carPriceField = NodeProvider.createTextField(width);

    carLeftWheelRadio = new RadioButton(CarWheelSideType.LEFT.getValue());
    RadioButton carRightWheelRadio = new RadioButton(CarWheelSideType.RIGHT.getValue());
    HBox radioPanel = NodeProvider.createHBox(Pos.CENTER, 10, carLeftWheelRadio, carRightWheelRadio);
    ToggleGroup group = new ToggleGroup();
    group.getToggles().addAll(carLeftWheelRadio, carRightWheelRadio);
    carLeftWheelRadio.setSelected(true);

    carFormPane = NodeProvider.createGridPane(Pos.TOP_CENTER, 10, 10);
    int row = 0;
    carFormPane.add(NodeProvider.createFormTextLabel("Marca: "), 0, row);
    carFormPane.add(carMakeText, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("Model: "), 0, row);
    carFormPane.add(carTypeText, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("Motorizare: "), 0, row);
    carFormPane.add(carNameField, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("Caroserie: "), 0, row);
    carFormPane.add(carBodyTypeCombo, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("An fabricatie: "), 0, row);
    carFormPane.add(productionYearPicker, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("De la: "), 0, row);
    carFormPane.add(producedFromPicker, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("Pana la: "), 0, row);
    carFormPane.add(producedToPicker, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("Numar parc: "), 0, row);
    carFormPane.add(carParkNumberField, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("Km parcursi: "), 0, row);
    carFormPane.add(carKmField, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("KW: "), 0, row);
    carFormPane.add(carKwSpinner, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("Cap. cil.:"), 0, row);
    carFormPane.add(carCapacitySpinner, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("Cilindrii: "), 0, row);
    carFormPane.add(carCylindersSpinner, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("Tip motor: "), 0, row);
    carFormPane.add(enginesTextField, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("Combustibil: "), 0, row);
    carFormPane.add(carFuelCombo, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("Cod Culoare: "), 0, row);
    carFormPane.add(carColorCodeField, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("Pret Achizitie: "), 0, row);
    carFormPane.add(carPriceField, 1, row++);
    carFormPane.add(NodeProvider.createFormTextLabel("Volan: "), 0, row);
    carFormPane.add(radioPanel, 1, row);

    Tooltip.install(enginesTextField, new Tooltip("Daca mai multe serii de motor sunt disponibile acestea se vor separa prin virgula"));

    carFormPane.setPadding(new Insets(5, 0, 10, 0));
  }

  public Text getCarMakeText() {
    return carMakeText;
  }

  public Text getCarTypeText() {
    return carTypeText;
  }

  public TextField getCarNameField() {
    return carNameField;
  }

  public ComboBox<CarBodyTypeModel> getCarBodyTypeCombo() {
    return carBodyTypeCombo;
  }

  public DatePicker getProducedFromPicker() {
    return producedFromPicker;
  }

  public DatePicker getProducedToPicker() {
    return producedToPicker;
  }

  public DatePicker getProductionYearPicker() {
    return productionYearPicker;
  }

  public TextField getCarParkNumberField() {
    return carParkNumberField;
  }

  public TextField getCarKmField() {
    return carKmField;
  }

  public Spinner<Integer> getCarKwSpinner() {
    return carKwSpinner;
  }

  public Spinner<Integer> getCarCapacitySpinner() {
    return carCapacitySpinner;
  }

  public Spinner<Integer> getCarCylindersSpinner() {
    return carCylindersSpinner;
  }

  public TextField getEnginesTextField() {
    return enginesTextField;
  }

  public ComboBox<FuelModel> getCarFuelCombo() {
    return carFuelCombo;
  }

  public TextField getCarColorCodeField() {
    return carColorCodeField;
  }

  public TextField getCarPriceField() {
    return carPriceField;
  }

  public RadioButton getCarLeftWheelRadio() {
    return carLeftWheelRadio;
  }

  public Node asNode() {
    return carFormPane;
  }
}
