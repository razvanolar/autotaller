package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 04.05.2017
 */
public class AdminRegisterCarDialogView implements AdminRegisterCarDialogController.IAdminRegisterCarDialogView {

  private GridPane gridPane;

  public AdminRegisterCarDialogView() {
    init();
  }

  private void init() {
    ComboBox<CarMakeModel> carMakesCombo = NodeProvider.createCarMakesCombo();
    ComboBox<CarTypeModel> carTypesCombo = NodeProvider.createCarTypesCombo();
    TextField carNameField = NodeProvider.createTextField();
    DatePicker fromDatePicker = NodeProvider.createDatePicker();
    DatePicker toDatePicker = NodeProvider.createDatePicker();
    Spinner<Integer> kwSpinner = NodeProvider.createSpinner(1, 1000, 50, 1);
    Spinner<Integer> capCilSpinner = NodeProvider.createSpinner(1, 10000, 1000, 1);
    Spinner<Integer> cilindersSpinner = NodeProvider.createSpinner(1, 50, 4, 1);
    TextField engineField = NodeProvider.createTextField();
    Text addComponentsLink = NodeProvider.createTextLabel("Adauga Componente", 16, true);

    HBox addComponentsContainer = new HBox(addComponentsLink);
    addComponentsContainer.setAlignment(Pos.CENTER_RIGHT);

    gridPane = NodeProvider.createGridPane(Pos.CENTER, 10,10);
    gridPane.add(createCategoryHBox("Atribute Masina"), 0, 0, 2, 1);
    gridPane.add(NodeProvider.createFormTextLabel("Marca: "), 0, 1);
    gridPane.add(carMakesCombo, 1, 1);
    gridPane.add(NodeProvider.createFormTextLabel("Model: "), 0, 2);
    gridPane.add(carTypesCombo, 1, 2);
    gridPane.add(NodeProvider.createFormTextLabel("Nume: "), 0, 3);
    gridPane.add(carNameField, 1, 3);
    gridPane.add(NodeProvider.createFormTextLabel("De la: "), 0, 4);
    gridPane.add(fromDatePicker, 1, 4);
    gridPane.add(NodeProvider.createFormTextLabel("Pana la: "), 0, 5);
    gridPane.add(toDatePicker, 1, 5);
    gridPane.add(NodeProvider.createFormTextLabel("KW: "), 0, 6);
    gridPane.add(kwSpinner, 1, 6);
    gridPane.add(NodeProvider.createFormTextLabel("Capacitate: "), 0, 7);
    gridPane.add(capCilSpinner, 1, 7);
    gridPane.add(NodeProvider.createFormTextLabel("Cilindrii: "), 0, 8);
    gridPane.add(cilindersSpinner, 1, 8);
    gridPane.add(NodeProvider.createFormTextLabel("Cod motor: "), 0, 9);
    gridPane.add(engineField, 1, 9);
    gridPane.add(addComponentsContainer, 0, 10, 2, 1);
  }

  private HBox createCategoryHBox(String name) {
    HBox hBox = new HBox();
    hBox.setAlignment(Pos.CENTER_LEFT);
    hBox.getChildren().add(NodeProvider.createTextLabel(name, 20, false));
    return hBox;
  }

  @Override
  public Node asNode() {
    return gridPane;
  }
}
