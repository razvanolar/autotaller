package com.autotaller.app.components.app_view.admin_view.admin_components_view.utils;

import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.utils.StockType;
import com.autotaller.app.utils.StringValidator;
import com.autotaller.app.utils.UsageStateType;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.resources.NodeProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AdminEditComponentView implements View {

  private GridPane mainContainer;
  private CarComponentModel carComponent;

  private TextField nameTextField;
  private TextField codeTextField;
  private ComboBox<StockType> stockTypeComboBox;
  private Spinner<Integer> piecesSpinner;
  private ComboBox<UsageStateType> usageComboBox;
  private Spinner<Integer> priceSpinner;

  private int minPiecesNo;

  public AdminEditComponentView(CarComponentModel carComponent) {
    this.carComponent = carComponent;
    this.minPiecesNo = carComponent.getSoldPieces();
    init();
    addListeners();
  }

  private void init() {
    nameTextField = NodeProvider.createTextField();
    codeTextField = NodeProvider.createTextField();
    stockTypeComboBox = NodeProvider.createCarComponentStockTypeCombo();
    piecesSpinner = NodeProvider.createSpinner(minPiecesNo, Integer.MAX_VALUE, carComponent.getInitialPieces(), 1);
    usageComboBox = NodeProvider.createCarComponentUsageTypeCombo();
    priceSpinner = NodeProvider.createSpinner(1, Integer.MAX_VALUE, carComponent.getPrice(), 1);

    nameTextField.setText(carComponent.getName());
    codeTextField.setText(carComponent.getCode());
    stockTypeComboBox.getItems().addAll(StockType.values());
    stockTypeComboBox.setValue(carComponent.getStock());
    usageComboBox.getItems().addAll(UsageStateType.values());
    usageComboBox.setValue(carComponent.getUsageState());

    mainContainer = NodeProvider.createGridPane(Pos.CENTER, 5, 10);
    mainContainer.setPadding(new Insets(15));
    mainContainer.getStyleClass().add(StyleProvider.WHITE_BACKGROUND_CLASS);

    int row = 0;
    mainContainer.add(NodeProvider.createFormTextLabel("Sub-Ansamblu: "), 0, row);
    mainContainer.add(NodeProvider.createFormTextLabel(carComponent.getCarSubkitName()), 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Nume: "), 0, row);
    mainContainer.add(nameTextField, 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Cod: "), 0, row);
    mainContainer.add(codeTextField, 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Stoc: "), 0, row);
    mainContainer.add(stockTypeComboBox, 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Bucati (initial): "), 0, row);
    mainContainer.add(piecesSpinner, 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Grad uzura: "), 0, row);
    mainContainer.add(usageComboBox, 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Pret: "), 0, row);
    mainContainer.add(priceSpinner, 1, row);
  }

  private void addListeners() {
    nameTextField.textProperty().addListener((observable, oldValue, newValue) -> carComponent.setName(newValue));
    codeTextField.textProperty().addListener((observable, oldValue, newValue) -> carComponent.setCode(newValue));
    stockTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> carComponent.setStock(newValue));
    usageComboBox.valueProperty().addListener((observable, oldValue, newValue) -> carComponent.setUsageState(newValue));
    piecesSpinner.valueProperty().addListener((observable, oldValue, newValue) -> carComponent.setInitialPieces(newValue));
    priceSpinner.valueProperty().addListener((observable, oldValue, newValue) -> carComponent.setPrice(newValue));
    piecesSpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
      if (!StringValidator.isPositiveInteger(newValue)) {
        return;
      }
      carComponent.setInitialPieces(Integer.parseInt(newValue));
    });
    priceSpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
      if (!StringValidator.isPositiveInteger(newValue)) {
        return;
      }
      carComponent.setPrice(Integer.parseInt(newValue));
    });
  }

  public CarComponentModel getCarComponent() {
    return carComponent;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
