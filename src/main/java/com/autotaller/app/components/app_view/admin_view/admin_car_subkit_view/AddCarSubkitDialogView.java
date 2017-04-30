package com.autotaller.app.components.app_view.admin_view.admin_car_subkit_view;

import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 30.04.2017
 */
public class AddCarSubkitDialogView implements AddCarSubkitDialogController.IAddCarSubkitDialogView {

  private GridPane gridPane;
  private ComboBox<CarKitModel> carKitsCombo;
  private TextField carSubkitNameField;
  private CheckBox gasolineCheckBox;
  private CheckBox dieselCheckBox;
  private CheckBox gplCheckBox;
  private CheckBox electricCheckBox;

  private static int FIELD_WIDTH = 220;

  public AddCarSubkitDialogView() {
    init();
  }

  private void init() {
    carKitsCombo = NodeProvider.createCarKitCombo(FIELD_WIDTH);
    carSubkitNameField = NodeProvider.createTextField(FIELD_WIDTH);
    gasolineCheckBox = new CheckBox();
    dieselCheckBox = new CheckBox();
    gplCheckBox = new CheckBox();
    electricCheckBox = new CheckBox();

    gridPane = new GridPane();
    gridPane.add(NodeProvider.createFormTextLabel("Ansamblu: "), 0 ,0);
    gridPane.add(carKitsCombo, 1, 0);
    gridPane.add(NodeProvider.createFormTextLabel("Nume: "), 0, 1);
    gridPane.add(carSubkitNameField, 1, 1);
    gridPane.add(NodeProvider.createFormTextLabel("Benzina: "), 0, 2);
    gridPane.add(gasolineCheckBox, 1, 2);
    gridPane.add(NodeProvider.createFormTextLabel("Diesel: "), 0, 3);
    gridPane.add(dieselCheckBox, 1, 3);
    gridPane.add(NodeProvider.createFormTextLabel("GPL: "), 0, 4);
    gridPane.add(gplCheckBox, 1, 4);
    gridPane.add(NodeProvider.createFormTextLabel("Electric: "), 0, 5);
    gridPane.add(electricCheckBox, 1, 5);

    gridPane.setAlignment(Pos.CENTER);
    gridPane.setVgap(10);
    gridPane.setVgap(10);
  }

  public ComboBox<CarKitModel> getCarKitsCombo() {
    return carKitsCombo;
  }

  public TextField getCarSubkitNameField() {
    return carSubkitNameField;
  }

  public CheckBox getGasolineCheckBox() {
    return gasolineCheckBox;
  }

  public CheckBox getDieselCheckBox() {
    return dieselCheckBox;
  }

  public CheckBox getGplCheckBox() {
    return gplCheckBox;
  }

  public CheckBox getElectricCheckBox() {
    return electricCheckBox;
  }

  @Override
  public Node asNode() {
    return gridPane;
  }
}
