package com.autotaller.app.components.app_view.admin_view.admin_car_model_view;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import com.jfoenix.controls.JFXDatePicker;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AddCarModelDialogView implements AddCarModelDialogController.IAddCarModelDialogView {

  private GridPane gridPane;
  private ComboBox<CarMakeModel> carMakesCombo;
  private TextField nameTextField;
  private JFXDatePicker fromDatePicker;
  private JFXDatePicker toDatePicker;

  public AddCarModelDialogView() {
    init();
  }

  private void init() {
    carMakesCombo = new ComboBox<>();
    nameTextField = new TextField();
    fromDatePicker = new JFXDatePicker();
    toDatePicker = new JFXDatePicker();

    carMakesCombo.setPrefWidth(220);
    nameTextField.setPrefWidth(220);

    gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    gridPane.add(NodeProvider.createTextLabel("Marca: ", 13, false), 0, 0);
    gridPane.add(carMakesCombo, 1, 0);
    gridPane.add(NodeProvider.createTextLabel("Nume: ", 13, false), 0, 1);
    gridPane.add(nameTextField, 1, 1);
    gridPane.add(NodeProvider.createTextLabel("De la: ", 13, false), 0, 2);
    gridPane.add(fromDatePicker, 1, 2);
    gridPane.add(NodeProvider.createTextLabel("Pane la: ", 13, false), 0, 3);
    gridPane.add(toDatePicker, 1, 3);
  }

  public ComboBox<CarMakeModel> getCarMakesCombo() {
    return carMakesCombo;
  }

  @Override
  public Node asNode() {
    return gridPane;
  }
}
