package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AddCarModelDialogView implements AddCarModelDialogController.IAddCarModelDialogView {

  private GridPane gridPane;
  private ComboBox<CarMakeModel> carMakesCombo;
  private TextField nameTextField;
  private DatePicker fromDatePicker;
  private DatePicker toDatePicker;

  private static int FIELD_WIDTH = 220;
  private int row_index;

  public AddCarModelDialogView() {
    init();
  }

  private void init() {
    carMakesCombo = NodeProvider.createCarMakesCombo(FIELD_WIDTH);
    nameTextField = NodeProvider.createTextField(FIELD_WIDTH);
    fromDatePicker = NodeProvider.createDatePicker(FIELD_WIDTH);
    toDatePicker = NodeProvider.createDatePicker(FIELD_WIDTH);

    gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    gridPane.add(NodeProvider.createTextLabel("Marca: ", 13, false), 0, row_index);
    gridPane.add(carMakesCombo, 1, row_index++);
    gridPane.add(NodeProvider.createTextLabel("Model: ", 13, false), 0, row_index);
    gridPane.add(nameTextField, 1, row_index++);
    gridPane.add(NodeProvider.createTextLabel("De la: ", 13, false), 0, row_index);
    gridPane.add(fromDatePicker, 1, row_index++);
    gridPane.add(NodeProvider.createTextLabel("Pane la: ", 13, false), 0, row_index);
    gridPane.add(toDatePicker, 1, row_index++);
  }

  public ComboBox<CarMakeModel> getCarMakesCombo() {
    return carMakesCombo;
  }

  public TextField getNameTextField() {
    return nameTextField;
  }

  public DatePicker getFromDatePicker() {
    return fromDatePicker;
  }

  public DatePicker getToDatePicker() {
    return toDatePicker;
  }

  @Override
  public Node asNode() {
    return gridPane;
  }
}
