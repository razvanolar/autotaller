package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.model.FuelModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 04.05.2017
 */
public class AdminSaveCarView extends IterableView implements AdminSaveCarController.IAdminSaveCarView {

  private SplitPane splitPane;
  private GridPane gridPane;
  private ScrollPane componentsScrollPane;
  private ComboBox<CarMakeModel> carMakesCombo;
  private ComboBox<CarTypeModel> carTypesCombo;
  private ComboBox<FuelModel> fuelCombo;
  private TextField carNameField;
  private DatePicker fromDatePicker;
  private DatePicker toDatePicker;
  private Spinner<Integer> kwSpinner;
  private Spinner<Integer> capCilSpinner;
  private Spinner<Integer> cilindersSpinner;
  private TextField engineField;
  private Text addComponentsLink;

  private Button saveCarButton;

  public AdminSaveCarView() {
    init();
  }

  private void init() {
    carMakesCombo = NodeProvider.createCarMakesCombo();
    carTypesCombo = NodeProvider.createCarTypesCombo();
    fuelCombo = NodeProvider.createFuelCombo();
    carNameField = NodeProvider.createTextField();
    fromDatePicker = NodeProvider.createDatePicker();
    toDatePicker = NodeProvider.createDatePicker();
    kwSpinner = NodeProvider.createSpinner(1, 1000, 50, 1);
    capCilSpinner = NodeProvider.createSpinner(1, 10000, 1000, 1);
    cilindersSpinner = NodeProvider.createSpinner(1, 50, 4, 1);
    engineField = NodeProvider.createTextField();
    addComponentsLink = NodeProvider.createTextLabel("Adauga Componente", 16, true);
    saveCarButton = new Button("Salveaza");

    toolBar.getItems().addAll(new Separator(), saveCarButton);

    HBox addComponentsLabelContainer = new HBox(addComponentsLink);
    addComponentsLabelContainer.setAlignment(Pos.CENTER_RIGHT);

    int row = 0;
    gridPane = NodeProvider.createGridPane(Pos.CENTER, 10,10);
    gridPane.add(createCategoryHBox("Atribute Masina"), 0, row++, 2, 1);
    gridPane.add(NodeProvider.createFormTextLabel("Marca: "), 0, row);
    gridPane.add(carMakesCombo, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Model: "), 0, row);
    gridPane.add(carTypesCombo, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Nume: "), 0, row);
    gridPane.add(carNameField, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("De la: "), 0, row);
    gridPane.add(fromDatePicker, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Pana la: "), 0, row);
    gridPane.add(toDatePicker, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("KW: "), 0, row);
    gridPane.add(kwSpinner, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Capacitate: "), 0, row);
    gridPane.add(capCilSpinner, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Cilindrii: "), 0, row);
    gridPane.add(cilindersSpinner, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Cod motor: "), 0, row);
    gridPane.add(engineField, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Combustibil: "), 0, row);
    gridPane.add(fuelCombo, 1, row++);
    gridPane.add(addComponentsLabelContainer, 0, row, 2, 1);

    splitPane = new SplitPane(gridPane);
    borderPane.setCenter(splitPane);
  }

  @Override
  public void showComponentsView() {
    if (componentsScrollPane == null)
      initComponentsPane();
  }

  @Override
  public void hideComponentsView() {

  }

  private void initComponentsPane() {

//    componentsScrollPane = NodeProvider.createScrollPane();
  }

  private HBox createCategoryHBox(String name) {
    HBox hBox = new HBox();
    hBox.setAlignment(Pos.CENTER_LEFT);
    hBox.getChildren().add(NodeProvider.createTextLabel(name, 23, false));
    return hBox;
  }

  public ComboBox<CarMakeModel> getCarMakesCombo() {
    return carMakesCombo;
  }

  public ComboBox<CarTypeModel> getCarTypesCombo() {
    return carTypesCombo;
  }

  public TextField getCarNameField() {
    return carNameField;
  }

  public DatePicker getFromDatePicker() {
    return fromDatePicker;
  }

  public DatePicker getToDatePicker() {
    return toDatePicker;
  }

  public Spinner<Integer> getKwSpinner() {
    return kwSpinner;
  }

  public Spinner<Integer> getCapCilSpinner() {
    return capCilSpinner;
  }

  public Spinner<Integer> getCilindersSpinner() {
    return cilindersSpinner;
  }

  public TextField getEngineField() {
    return engineField;
  }

  public Text getAddComponentsLink() {
    return addComponentsLink;
  }

  @Override
  public Node asNode() {
    return borderPane;
  }
}
