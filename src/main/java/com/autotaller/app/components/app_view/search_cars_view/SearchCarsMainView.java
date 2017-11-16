package com.autotaller.app.components.app_view.search_cars_view;

import com.autotaller.app.components.app_view.utils.extensions.BodyTypeCheckBox;
import com.autotaller.app.components.app_view.utils.extensions.FuelCheckBox;
import com.autotaller.app.components.app_view.utils.extensions.WheelTypeCheckBox;
import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarBodyTypeModel;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.model.FuelModel;
import com.autotaller.app.utils.CarWheelSideType;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SearchCarsMainView extends IterableView implements SearchCarsMainController.ISearchCarsMainView {

  private ComboBox<CarMakeModel> carMakeComboBox;
  private ComboBox<CarTypeModel> carTypeComboBox;
  private TextField parkNumberTextField;
  private TextField productionYearTextField;
  private WheelTypeCheckBox leftWheelCheckBox;
  private WheelTypeCheckBox rightWheelCheckBox;
  private TextField powerFromTextField;
  private TextField powerToTextField;
  private RadioButton kwPowerRadio;
  private RadioButton cpPowerRadio;
  private TextField kmFromTextField;
  private TextField kmToTextField;
  private TextField capacityFromTextField;
  private TextField capacityToTextField;
  private TextField priceFromTextField;
  private TextField priceToTextField;
  private Button searchButton;

  private List<FuelCheckBox> fuelCheckBoxList;
  private List<BodyTypeCheckBox> bodyTypeCheckBoxList;

  private HBox fuelsPanel;
  private GridPane bodyTypesPanel;

  public SearchCarsMainView() {
    fuelCheckBoxList = new ArrayList<>();
    bodyTypeCheckBoxList = new ArrayList<>();
    init();
  }

  private void init() {
    Text title = NodeProvider.createTextLabel("Cautare Masini", 25, false);
    carMakeComboBox = NodeProvider.createCarMakesCombo();
    carTypeComboBox = NodeProvider.createCarTypesCombo();
    parkNumberTextField = NodeProvider.createTextField();
    productionYearTextField = NodeProvider.createTextField();
    leftWheelCheckBox = new WheelTypeCheckBox(CarWheelSideType.LEFT);
    rightWheelCheckBox = new WheelTypeCheckBox(CarWheelSideType.RIGHT);
    powerToTextField = NodeProvider.createTextField(80);
    powerFromTextField = NodeProvider.createTextField(80);
    kwPowerRadio = new RadioButton("KW");
    cpPowerRadio = new RadioButton("CP");
    kmFromTextField = NodeProvider.createTextField(80);
    kmToTextField = NodeProvider.createTextField(80);
    capacityFromTextField = NodeProvider.createTextField(80);
    capacityToTextField = NodeProvider.createTextField(80);
    priceFromTextField = NodeProvider.createTextField(80);
    priceToTextField = NodeProvider.createTextField(80);
    searchButton = NodeProvider.createToolbarButton("Cauta", null);

    fuelsPanel = NodeProvider.createHBox(Pos.CENTER_LEFT, 10);
    bodyTypesPanel = NodeProvider.createGridPane(Pos.CENTER_LEFT, 10, 10);

    toolBar.getItems().addAll(new FillToolItem(), searchButton);

    int row = 0;
    GridPane gridPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    gridPane.add(NodeProvider.createHBox(Pos.CENTER, 10, title), 0, row++, 2, 1);
    gridPane.add(NodeProvider.createFormTextLabel("Marca: "), 0, row);
    gridPane.add(carMakeComboBox, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Model: "), 0, row);
    gridPane.add(carTypeComboBox, 1, row++);
    gridPane.add(NodeProvider.createHSeparatorPane(2), 0, row++, 2, 1);
    gridPane.add(NodeProvider.createFormTextLabel("Numar Parc: "), 0, row);
    gridPane.add(parkNumberTextField, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("An fabricatie: "), 0, row);
    gridPane.add(productionYearTextField, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Volan: "), 0, row);
    gridPane.add(NodeProvider.createHBox(Pos.CENTER_LEFT, 15, leftWheelCheckBox, rightWheelCheckBox), 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Putere: "), 0, row);
    gridPane.add(NodeProvider.createHBox(Pos.CENTER_LEFT, 10, powerFromTextField, NodeProvider.createFormTextLabel("-"), powerToTextField, kwPowerRadio, cpPowerRadio), 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Kilometrii: "), 0, row);
    gridPane.add(NodeProvider.createHBox(Pos.CENTER_LEFT, 10, kmFromTextField, NodeProvider.createFormTextLabel("-"), kmToTextField), 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Capacitate: "), 0, row);
    gridPane.add(NodeProvider.createHBox(Pos.CENTER_LEFT, 10, capacityFromTextField, NodeProvider.createFormTextLabel("-"), capacityToTextField, NodeProvider.createFormTextLabel("cm3")), 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Pret: "), 0, row);
    gridPane.add(NodeProvider.createHBox(Pos.CENTER_LEFT, 10, priceFromTextField, NodeProvider.createFormTextLabel("-"), priceToTextField), 1, row++);
    gridPane.add(NodeProvider.createHSeparatorPane(2), 0, row++, 2, 1);
    gridPane.add(NodeProvider.createFormTextLabel("Combustibil: "), 0, row);
    gridPane.add(fuelsPanel, 1, row++);
    gridPane.add(NodeProvider.createHSeparatorPane(2), 0, row++, 2, 1);
    gridPane.add(NodeProvider.createFormTextLabel("Caroserie: "), 0, row);
    gridPane.add(bodyTypesPanel, 1, row);

    borderPane.setCenter(NodeProvider.createScrollPane(gridPane, true));

    ToggleGroup group = new ToggleGroup();
    group.getToggles().addAll(kwPowerRadio, cpPowerRadio);
    kwPowerRadio.setSelected(true);
    leftWheelCheckBox.setSelected(true);
    rightWheelCheckBox.setSelected(true);
  }

  public void injectFuelModels(List<FuelModel> fuels) {
    if (fuels == null)
      return;
    for (FuelModel fuel : fuels) {
      FuelCheckBox fuelCheckBox = new FuelCheckBox(fuel);
      fuelCheckBox.setSelected(true);
      fuelsPanel.getChildren().add(fuelCheckBox);
      fuelCheckBoxList.add(fuelCheckBox);
    }
  }

  public void injectBodyTypeModels(List<CarBodyTypeModel> bodyTypes) {
    if (bodyTypes == null)
      return;
    for (int i=0; i<bodyTypes.size(); i++) {
      CarBodyTypeModel bodyType = bodyTypes.get(i);
      BodyTypeCheckBox bodyTypeCheckBox = new BodyTypeCheckBox(bodyType);
      bodyTypeCheckBox.setSelected(true);
      bodyTypeCheckBoxList.add(bodyTypeCheckBox);
      bodyTypesPanel.add(bodyTypeCheckBox, i % 3, i / 3);
    }
  }

  public ComboBox<CarMakeModel> getCarMakeComboBox() {
    return carMakeComboBox;
  }

  public ComboBox<CarTypeModel> getCarTypeComboBox() {
    return carTypeComboBox;
  }

  public TextField getParkNumberTextField() {
    return parkNumberTextField;
  }

  public TextField getProductionYearTextField() {
    return productionYearTextField;
  }

  public WheelTypeCheckBox getLeftWheelCheckBox() {
    return leftWheelCheckBox;
  }

  public WheelTypeCheckBox getRightWheelCheckBox() {
    return rightWheelCheckBox;
  }

  public TextField getPowerFromTextField() {
    return powerFromTextField;
  }

  public TextField getPowerToTextField() {
    return powerToTextField;
  }

  public RadioButton getKwPowerRadio() {
    return kwPowerRadio;
  }

  public TextField getKmFromTextField() {
    return kmFromTextField;
  }

  public TextField getKmToTextField() {
    return kmToTextField;
  }

  public TextField getCapacityFromTextField() {
    return capacityFromTextField;
  }

  public TextField getCapacityToTextField() {
    return capacityToTextField;
  }

  public TextField getPriceFromTextField() {
    return priceFromTextField;
  }

  public TextField getPriceToTextField() {
    return priceToTextField;
  }

  public List<FuelCheckBox> getFuelCheckBoxList() {
    return fuelCheckBoxList;
  }

  public List<BodyTypeCheckBox> getBodyTypeCheckBoxList() {
    return bodyTypeCheckBoxList;
  }

  public Button getSearchButton() {
    return searchButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
