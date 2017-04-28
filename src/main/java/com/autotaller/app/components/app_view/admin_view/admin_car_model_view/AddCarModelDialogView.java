package com.autotaller.app.components.app_view.admin_view.admin_car_model_view;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AddCarModelDialogView implements AddCarModelDialogController.IAddCarModelDialogView {

  private GridPane gridPane;
  private ComboBox<CarMakeModel> carMakesCombo;
  private TextField nameTextField;
  private DatePicker fromDatePicker;
  private DatePicker toDatePicker;
  private Button addEngineFieldButton;

  private List<TextField> engineTextFields;

  private static int FIELD_WIDTH = 220;
  private int row_index;

  public AddCarModelDialogView() {
    engineTextFields = new ArrayList<>();
    init();
    initHandlers();
  }

  private void init() {
    carMakesCombo = new ComboBox<>();
    nameTextField = new TextField();
    fromDatePicker = new DatePicker();
    toDatePicker = new DatePicker();
    addEngineFieldButton = NodeProvider.createButton("Adauga Camp");

    carMakesCombo.setPrefWidth(FIELD_WIDTH);
    nameTextField.setPrefWidth(FIELD_WIDTH);
    fromDatePicker.setPrefWidth(FIELD_WIDTH);
    toDatePicker.setPrefWidth(FIELD_WIDTH);

    gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    gridPane.add(NodeProvider.createTextLabel("Marca: ", 13, false), 0, row_index);
    gridPane.add(carMakesCombo, 1, row_index++);
    gridPane.add(NodeProvider.createTextLabel("Nume: ", 13, false), 0, row_index);
    gridPane.add(nameTextField, 1, row_index++);
    gridPane.add(NodeProvider.createTextLabel("De la: ", 13, false), 0, row_index);
    gridPane.add(fromDatePicker, 1, row_index++);
    gridPane.add(NodeProvider.createTextLabel("Pane la: ", 13, false), 0, row_index);
    gridPane.add(toDatePicker, 1, row_index++);
    Text textLabel = NodeProvider.createTextLabel("Motor: ", 13, false);
    gridPane.add(textLabel, 0, row_index);
    gridPane.add(createEngineField(false, textLabel), 1, row_index++);
    gridPane.add(addEngineFieldButton, 0, row_index);
  }

  private void initHandlers() {
    addEngineFieldButton.setOnAction(event -> {
      Text textLabel = NodeProvider.createTextLabel("Motor: ", 13, false);
      gridPane.add(textLabel, 0, row_index);
      gridPane.add(createEngineField(true, textLabel), 1, row_index++);
      gridPane.getChildren().remove(addEngineFieldButton);
      gridPane.add(addEngineFieldButton, 0, row_index);
    });
  }

  private HBox createEngineField(boolean carRemove, Text label) {
    HBox hBox = new HBox();
    TextField textField = new TextField();
    engineTextFields.add(textField);
    hBox.getChildren().add(textField);
    hBox.setAlignment(Pos.CENTER);
    if (carRemove) {
      Button removeButton = NodeProvider.createRemoveButton("Sterge");
      hBox.getChildren().add(removeButton);
      textField.setPrefWidth(FIELD_WIDTH - 50);
      removeButton.setOnAction(event -> {
        Integer hBoxIndex = GridPane.getRowIndex(hBox);
        Integer addButtonIndex = GridPane.getRowIndex(addEngineFieldButton);
        if (hBoxIndex == null || addButtonIndex == null || hBoxIndex + 1 != addButtonIndex) {
          //TODO inform user about this exception
          return;
        }
        hBox.getChildren().remove(textField);
        hBox.getChildren().remove(removeButton);
        removeButton.setOnAction(null);
        engineTextFields.remove(textField);

        gridPane.getChildren().remove(hBox);
        gridPane.getChildren().remove(label);
        row_index--;
        gridPane.getChildren().remove(addEngineFieldButton);
        gridPane.add(addEngineFieldButton, 0, row_index);
      });
    } else {
      textField.setPrefWidth(FIELD_WIDTH);
    }

    return hBox;
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

  public List<TextField> getEngineTextFields() {
    return engineTextFields;
  }

  @Override
  public Node asNode() {
    return gridPane;
  }
}
