package com.autotaller.app.components.app_view.admin_view.admin_car_model_view;

import com.autotaller.app.components.app_view.admin_view.AdminToolbarPane;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AdminCarModelView extends AdminToolbarPane implements AdminCarModelController.IAdminCarModelView {

  private BorderPane borderPane;

  private Button addCarModelButton;
  private ToggleButton filterButton;

  private SplitPane splitPane;
  private GridPane filterPane;
  private TableView<CarTypeModel> carModelTable;

  private double lastDividerPosition = .25;
  private ComboBox<CarMakeModel> carMakeCombo;
  private TextField carModelNameTextField;
  private DatePicker fromDatePicker;
  private DatePicker toDatePicker;
  private TextField engineTextField;

  private static int FIELD_WIDTH = 200;

  public AdminCarModelView() {
    super("Model");
    init();
    setContentNode(borderPane);
  }

  private void init() {
    createFilterPane();
    carModelTable = NodeProvider.createCarModelTable();

    splitPane = new SplitPane(carModelTable);

    addCarModelButton = new Button("Adauga Model");
    filterButton = new ToggleButton("Filtrare");
    addCarModelButton.setRotate(-90);
    filterButton.setRotate(-90);
    Group addCarModelGroup = new Group(addCarModelButton);
    Group filterGroup = new Group(filterButton);

    ToolBar toolBar = NodeProvider.createToolBar();
    toolBar.getItems().addAll(addCarModelGroup, filterGroup);
    toolBar.setOrientation(Orientation.VERTICAL);

    borderPane = new BorderPane(splitPane);
    borderPane.setLeft(toolBar);
    borderPane.setPrefHeight(500);
  }

  private void createFilterPane() {
    filterPane = new GridPane();
    carMakeCombo = NodeProvider.createCarMakesCombo(FIELD_WIDTH);
    carModelNameTextField = NodeProvider.createTextField(FIELD_WIDTH);
    fromDatePicker = NodeProvider.createDatePicker(FIELD_WIDTH);
    toDatePicker = NodeProvider.createDatePicker(FIELD_WIDTH);
    engineTextField = NodeProvider.createTextField(FIELD_WIDTH);

    filterPane.add(NodeProvider.createFormTextLabel("Marca: "), 0, 0);
    filterPane.add(carMakeCombo, 1, 0);
    filterPane.add(NodeProvider.createFormTextLabel("Nume Model: "), 0, 1);
    filterPane.add(carModelNameTextField, 1, 1);
    filterPane.add(NodeProvider.createFormTextLabel("De la: "), 0, 2);
    filterPane.add(fromDatePicker, 1, 2);
    filterPane.add(NodeProvider.createFormTextLabel("Pana la: "), 0, 3);
    filterPane.add(toDatePicker, 1, 3);
    filterPane.add(NodeProvider.createFormTextLabel("Motor: "), 0, 4);
    filterPane.add(engineTextField, 1, 4);

    filterPane.setAlignment(Pos.CENTER);
    filterPane.setVgap(10);
    filterPane.setHgap(10);
  }

  @Override
  protected void addToolbarButtons() {

  }

  public Button getAddCarModelButton() {
    return addCarModelButton;
  }

  public ToggleButton getFilterButton() {
    return filterButton;
  }

  public TableView<CarTypeModel> getCarModelTable() {
    return carModelTable;
  }

  public ComboBox<CarMakeModel> getCarMakeCombo() {
    return carMakeCombo;
  }

  public TextField getCarModelNameTextField() {
    return carModelNameTextField;
  }

  public DatePicker getFromDatePicker() {
    return fromDatePicker;
  }

  public DatePicker getToDatePicker() {
    return toDatePicker;
  }

  public TextField getEngineTextField() {
    return engineTextField;
  }

  public void showFilterPane() {
    if (!splitPane.getItems().contains(filterPane)) {
      splitPane.getItems().add(0, filterPane);
      splitPane.setDividerPositions(lastDividerPosition);
    }
  }

  public void hideFilterPane() {
    if (splitPane.getItems().contains(filterPane)) {
      double[] dividers = splitPane.getDividerPositions();
      if (dividers != null && dividers.length > 0)
        lastDividerPosition = dividers[0];
      splitPane.getItems().remove(filterPane);
    }
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
