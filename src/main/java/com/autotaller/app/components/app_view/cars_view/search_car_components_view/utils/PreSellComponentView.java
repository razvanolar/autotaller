package com.autotaller.app.components.app_view.cars_view.search_car_components_view.utils;

import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.model.utils.PreSellComponentModel;
import com.autotaller.app.utils.StringValidator;
import com.autotaller.app.utils.resources.NodeProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * Created by razvanolar on 03.06.2017
 */
public class PreSellComponentView {

  private CarComponentModel carComponent;
  private GridPane mainContainer;
  private TextField priceTextField;
  private CheckBox changePriceCheckBox;
  private Spinner<Integer> soldPiecesSpinner;

  public PreSellComponentView(CarComponentModel carComponent) {
    this.carComponent = carComponent;
    init();
    addListeners();
  }

  private void init() {
    priceTextField = NodeProvider.createTextField();
    changePriceCheckBox = new CheckBox("Alt pret");
    soldPiecesSpinner = NodeProvider.createSpinner(1, carComponent.getLeftPieces(), 1, 1);

    priceTextField.setDisable(true);
    priceTextField.setText(String.valueOf(carComponent.getPrice()));

    mainContainer = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    int row = 0;
    mainContainer.add(NodeProvider.createFormTextLabel("Nume: "), 0, row);
    mainContainer.add(NodeProvider.createFormTextLabel(carComponent.getName()), 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Cod: "), 0, row);
    mainContainer.add(NodeProvider.createFormTextLabel(carComponent.getCode()), 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Bucati disponibile: "), 0, row);
    mainContainer.add(NodeProvider.createFormTextLabel(String.valueOf(carComponent.getLeftPieces())), 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Grad de uzura: "), 0, row);
    mainContainer.add(NodeProvider.createFormTextLabel(carComponent.getUsageState().getName()), 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Pret: "), 0, row);
    mainContainer.add(priceTextField, 1, row);
    mainContainer.add(changePriceCheckBox, 2, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Bucati vandute: "), 0, row);
    mainContainer.add(soldPiecesSpinner, 1, row);

    mainContainer.setPadding(new Insets(10));
    mainContainer.getStyleClass().add(StyleProvider.WHITE_BACKGROUND_CLASS);
  }

  private void addListeners() {
    changePriceCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        priceTextField.setDisable(true);
        priceTextField.setText(String.valueOf(carComponent.getPrice()));
      } else {
        priceTextField.setDisable(false);
      }
    });
  }

  public boolean isValid() {
    return StringValidator.isPositiveInteger(priceTextField.getText());
  }

  public PreSellComponentModel collect() {
    return new PreSellComponentModel(carComponent, soldPiecesSpinner.getValue(), Integer.parseInt(priceTextField.getText()));
  }

  public Region asNode() {
    return mainContainer;
  }
}
