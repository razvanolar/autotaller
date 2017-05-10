package com.autotaller.app.components.utils.filter_views;

import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view.utils.YearsPanelView;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.model.FuelModel;
import com.autotaller.app.model.utils.YearsRange;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.resources.NodeProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * Created by razvanolar on 10.05.2017
 */
public class DefaultCarFilterView implements View {

  private GridPane gridPane;
  private YearsPanelView yearsPanelView;
  private ComboBox<CarMakeModel> makeCombo;
  private ComboBox<CarTypeModel> typeCombo;
  private TextField nameField;
  private Spinner<Integer> kwFromSpinner;
  private Spinner<Integer> kwToSpinner;
  private Spinner<Integer> capacityFromSpinner;
  private Spinner<Integer> capacityToSpinner;
  private Spinner<Integer> cilindersSpinner;
  private ComboBox<FuelModel> fuelCombo;
  private Button resetFiltersButton;
  private Button searchButton;
  private TextField engineTextField;
  private Button engineButton;
  private TextField frameTextField;
  private Button frameButton;

  public DefaultCarFilterView() {
    init();
  }

  private void init() {
    int spinnerWidth = 80;
    yearsPanelView = new YearsPanelView(NodeProvider.DEFAULT_FIELD_WIDTH + 100);
    yearsPanelView.showYearPanels(new YearsRange(1990, 2017));
    makeCombo = NodeProvider.createCarMakesCombo();
    typeCombo = NodeProvider.createCarTypesCombo();
    nameField = NodeProvider.createTextField();
    kwFromSpinner = NodeProvider.createSpinner(spinnerWidth,0, 1000, 0, 1);
    kwToSpinner = NodeProvider.createSpinner(spinnerWidth,0, 1000, 0, 1);
    capacityFromSpinner = NodeProvider.createSpinner(spinnerWidth, 0, 10000, 0, 1);
    capacityToSpinner = NodeProvider.createSpinner(spinnerWidth, 0, 10000, 0, 1);
    cilindersSpinner = NodeProvider.createSpinner(spinnerWidth,0, 50, 0, 1);
    fuelCombo = NodeProvider.createFuelCombo();
    resetFiltersButton = NodeProvider.createButton("Reseteaza");
    searchButton = NodeProvider.createButton("Cauta");
    engineTextField = NodeProvider.createTextField();
    engineButton = NodeProvider.createButton("Cauta motor");
    frameTextField = NodeProvider.createTextField();
    frameButton = NodeProvider.createButton("Cauta sasiu");

    HBox kwHBox = new HBox(10, kwFromSpinner, NodeProvider.createFormTextLabel("-"), kwToSpinner);
    kwHBox.setAlignment(Pos.CENTER_LEFT);

    HBox capCilHBox = new HBox(10, capacityFromSpinner, NodeProvider.createFormTextLabel("-"), capacityToSpinner);
    capCilHBox.setAlignment(Pos.CENTER_LEFT);

    HBox filterButtonsContainer = new HBox(10, resetFiltersButton, searchButton);
    filterButtonsContainer.setAlignment(Pos.CENTER_RIGHT);
    resetFiltersButton.setPrefWidth(100);
    searchButton.setPrefWidth(100);

    HBox engineButtonContainer = new HBox(engineButton);
    engineButtonContainer.setAlignment(Pos.CENTER_RIGHT);
    engineButton.setPrefWidth(100);

    HBox frameButtonContainer = new HBox(frameButton);
    frameButtonContainer.setAlignment(Pos.CENTER_RIGHT);
    frameButton.setPrefWidth(100);

    gridPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    int row = 0;
    gridPane.add(yearsPanelView.asNode(), 0, row++, 2, 1);
    gridPane.add(createSeparator(), 0, row++, 2, 1);
    gridPane.add(NodeProvider.createFormTextLabel("Marca: "), 0, row);
    gridPane.add(makeCombo, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Model: "), 0, row);
    gridPane.add(typeCombo, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Nume: "), 0, row);
    gridPane.add(nameField, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("KW: "), 0, row);
    gridPane.add(kwHBox, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Cap. cil.: "), 0, row);
    gridPane.add(capCilHBox, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Cilindrii: "), 0, row);
    gridPane.add(cilindersSpinner, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Combustibil: "), 0, row);
    gridPane.add(fuelCombo, 1, row++);
    gridPane.add(filterButtonsContainer, 0, row++, 2, 1);
    gridPane.add(createSeparator(), 0, row++, 2, 1);
    gridPane.add(NodeProvider.createFormTextLabel("Serie motor: "), 0, row);
    gridPane.add(engineTextField, 1, row++);
    gridPane.add(engineButtonContainer, 0, row++, 2, 1);
    gridPane.add(createSeparator(), 0, row++, 2, 1);
    gridPane.add(NodeProvider.createFormTextLabel("Serie sasiu: "), 0, row);
    gridPane.add(frameTextField, 1, row++);
    gridPane.add(frameButtonContainer, 0, row++, 2, 1);
  }

  private HBox createSeparator() {
    HBox hBox = new HBox();
    hBox.setPrefHeight(8);
    hBox.getStyleClass().add(StyleProvider.ADMIN_SUB_TOOLBAR_PANE_CLASS);
    return hBox;
  }

  public YearsPanelView getYearsPanelView() {
    return yearsPanelView;
  }

  public ComboBox<CarMakeModel> getMakeCombo() {
    return makeCombo;
  }

  public ComboBox<CarTypeModel> getTypeCombo() {
    return typeCombo;
  }

  public TextField getNameField() {
    return nameField;
  }

  public Spinner<Integer> getKwFromSpinner() {
    return kwFromSpinner;
  }

  public Spinner<Integer> getKwToSpinner() {
    return kwToSpinner;
  }

  public Spinner<Integer> getCapacityFromSpinner() {
    return capacityFromSpinner;
  }

  public Spinner<Integer> getCapacityToSpinner() {
    return capacityToSpinner;
  }

  public Spinner<Integer> getCilindersSpinner() {
    return cilindersSpinner;
  }

  public ComboBox<FuelModel> getFuelCombo() {
    return fuelCombo;
  }

  public Button getResetFiltersButton() {
    return resetFiltersButton;
  }

  public Button getSearchButton() {
    return searchButton;
  }

  public TextField getEngineTextField() {
    return engineTextField;
  }

  public Button getEngineButton() {
    return engineButton;
  }

  public TextField getFrameTextField() {
    return frameTextField;
  }

  public Button getFrameButton() {
    return frameButton;
  }

  public Region asRegion() {
    return gridPane;
  }

  @Override
  public Node asNode() {
    return gridPane;
  }
}
