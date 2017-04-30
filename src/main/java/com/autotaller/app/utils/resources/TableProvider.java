package com.autotaller.app.utils.resources;

import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.model.CarSubkitModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.StringValidator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 * Created by razvanolar on 30.04.2017
 */
public class TableProvider {

  @SuppressWarnings("unchecked")
  public TableView<CarTypeModel> createCarModelTable() {
    TableView<CarTypeModel> table = new TableView<>();
    TableColumn<CarTypeModel, String> nameColumn = new TableColumn<>("Nume Model");
    TableColumn<CarTypeModel, String> fromColumn = new TableColumn<>("De la");
    TableColumn<CarTypeModel, String> toColumn = new TableColumn<>("Pana la");
    TableColumn<CarTypeModel, String> makeColumn = new TableColumn<>("Marca");

    nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(.33));
    fromColumn.prefWidthProperty().bind(table.widthProperty().multiply(.17));
    toColumn.prefWidthProperty().bind(table.widthProperty().multiply(.17));
    makeColumn.prefWidthProperty().bind(table.widthProperty().multiply(.33));

    nameColumn.setCellValueFactory(p -> {
      CarTypeModel value = p.getValue();
      if (value != null) {
        if (!StringValidator.isNullOrEmpty(value.getEngines())) {
          return new SimpleStringProperty(value.getName() + " (" + value.getEngines() + ")");
        } else {
          return new SimpleStringProperty(value.getName());
        }
      } else {
        return new SimpleStringProperty();
      }
    });
    fromColumn.setCellValueFactory(p -> {
      CarTypeModel value = p.getValue();
      if (value != null && value.getFrom() != null) {
        return new SimpleStringProperty(value.getFrom().toString());
      } else {
        return new SimpleStringProperty();
      }
    });
    toColumn.setCellValueFactory(p -> {
      CarTypeModel value = p.getValue();
      if (value != null && value.getTo() != null) {
        return new SimpleStringProperty(value.getTo().toString());
      } else {
        return new SimpleStringProperty();
      }
    });
    makeColumn.setCellValueFactory(p -> {
      CarTypeModel value = p.getValue();
      if (value != null && value.getCarMake() != null) {
        return new SimpleStringProperty(value.getCarMake().getName());
      } else {
        return new SimpleStringProperty();
      }
    });

    nameColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);
    fromColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);
    toColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);
    makeColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);

    table.getColumns().addAll(nameColumn, fromColumn, toColumn, makeColumn);
    return table;
  }

  @SuppressWarnings("unchecked")
  public TableView<CarKitModel> createCarKitModelTable() {
    TableView<CarKitModel> table = new TableView<>();
    TableColumn<CarKitModel, String> nameColumn = new TableColumn<>("Nume");
    TableColumn<CarKitModel, String> categoryColumn = new TableColumn<>("Categorie");

    nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(.5));
    categoryColumn.prefWidthProperty().bind(table.widthProperty().multiply(.5));

    nameColumn.setCellValueFactory(p -> {
      CarKitModel value = p.getValue();
      if (value != null) {
        return new SimpleStringProperty(value.getName());
      } else {
        return new SimpleStringProperty();
      }
    });
    categoryColumn.setCellValueFactory(p -> {
      CarKitModel value = p.getValue();
      if (value != null && value.getKitCategory() != null) {
        return new SimpleStringProperty(value.getKitCategory().getName());
      } else {
        return new SimpleStringProperty();
      }
    });

    nameColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);
    categoryColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);

    table.getColumns().addAll(nameColumn, categoryColumn);
    return table;
  }

  @SuppressWarnings("unchecked")
  public TableView<CarSubkitModel> createCarSubkitModelTable() {
    TableView<CarSubkitModel> table = new TableView<>();
    TableColumn<CarSubkitModel, String> nameColumn = new TableColumn<>("Nume");
    TableColumn<CarSubkitModel, String> carKitColumn = new TableColumn<>("Ansamblu");
    TableColumn<CarSubkitModel, Boolean> gasolineColumn = new TableColumn<>("Benzina");
    TableColumn<CarSubkitModel, Boolean> dieselColumn = new TableColumn<>("Diesel");
    TableColumn<CarSubkitModel, Boolean> gplColumn = new TableColumn<>("GPL");
    TableColumn<CarSubkitModel, Boolean> electricColumn = new TableColumn<>("Electric");

    nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(.3));
    carKitColumn.prefWidthProperty().bind(table.widthProperty().multiply(.3));
    gasolineColumn.prefWidthProperty().bind(table.widthProperty().multiply(.1));
    dieselColumn.prefWidthProperty().bind(table.widthProperty().multiply(.1));
    gplColumn.prefWidthProperty().bind(table.widthProperty().multiply(.1));
    electricColumn.prefWidthProperty().bind(table.widthProperty().multiply(.1));

    nameColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleStringProperty(p.getValue().getName()) : new SimpleStringProperty());
    carKitColumn.setCellValueFactory(p -> p.getValue() != null && p.getValue().getCarKit() != null ? new SimpleStringProperty(p.getValue().getCarKit().getName()) : new SimpleStringProperty());
    gasolineColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleBooleanProperty(p.getValue().isUsedForGasoline()) : new SimpleBooleanProperty());
    dieselColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleBooleanProperty(p.getValue().isUsedForDiesel()) : new SimpleBooleanProperty());
    gplColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleBooleanProperty(p.getValue().isUsedForGPL()) : new SimpleBooleanProperty());
    electricColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleBooleanProperty(p.getValue().isUsedForElectric()) : new SimpleBooleanProperty());

    gasolineColumn.setCellFactory(param -> new CheckBoxTableCell<>());
    dieselColumn.setCellFactory(param -> new CheckBoxTableCell<>());
    gplColumn.setCellFactory(param -> new CheckBoxTableCell<>());
    electricColumn.setCellFactory(param -> new CheckBoxTableCell<>());

    nameColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);
    carKitColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);
    gasolineColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);
    dieselColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);
    gplColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);
    electricColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);

    table.getColumns().addAll(nameColumn, carKitColumn, gasolineColumn, dieselColumn, gplColumn, electricColumn);
    return table;
  }
}
