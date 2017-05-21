package com.autotaller.app.utils.resources;

import com.autotaller.app.model.*;
import com.autotaller.app.utils.filters.ModelFilter;
import com.autotaller.app.utils.StringValidator;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 * Created by razvanolar on 30.04.2017
 */
public class TableProvider {

  @SuppressWarnings("unchecked")
  public TableView<CarMakeModel> createCarMakeTable() {
    TableView<CarMakeModel> table = new TableView<>();
    TableColumn<CarMakeModel, String> nameColumn = new TableColumn<>("Nume Marca");

    nameColumn.prefWidthProperty().bind(table.widthProperty());

    nameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue() != null ? p.getValue().getName() : ""));

    nameColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);

    table.getColumns().add(nameColumn);
    return table;
  }

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
        if (!StringValidator.isNullOrEmpty(value.getFrames())) {
          return new SimpleStringProperty(value.getName() + " (" + value.getFrames() + ")");
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

  @SuppressWarnings("unchecked")
  public TableView<CarModel> createCarTable() {
    TableView<CarModel> table = new TableView<>();
    TableColumn<CarModel, String> nameColumn = new TableColumn<>("Nume");
    TableColumn<CarModel, String> modelColumn = new TableColumn<>("Model");
    TableColumn<CarModel, String> fromColumn = new TableColumn<>("De la");
    TableColumn<CarModel, String> toColumn = new TableColumn<>("Pana la");
    TableColumn<CarModel, Integer> kwColumn = new TableColumn<>("KW");
    TableColumn<CarModel, Integer> hpColumn = new TableColumn<>("CP");
    TableColumn<CarModel, Integer> capacityColumn = new TableColumn<>("Cap. cil.");
    TableColumn<CarModel, Integer> cilindersColumn = new TableColumn<>("Cilindrii");
    TableColumn<CarModel, String> enginesColumn = new TableColumn<>("Cod motor");

    nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(.15));
    modelColumn.prefWidthProperty().bind(table.widthProperty().multiply(.15));
    DoubleBinding widthProp = table.widthProperty().multiply(.1);
    fromColumn.prefWidthProperty().bind(widthProp);
    toColumn.prefWidthProperty().bind(widthProp);
    kwColumn.prefWidthProperty().bind(widthProp);
    hpColumn.prefWidthProperty().bind(widthProp);
    capacityColumn.prefWidthProperty().bind(widthProp);
    cilindersColumn.prefWidthProperty().bind(widthProp);
    enginesColumn.prefWidthProperty().bind(widthProp);

    nameColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleStringProperty(p.getValue().getName()) : new SimpleStringProperty());
    modelColumn.setCellValueFactory(p -> p.getValue() != null && p.getValue().getCarType() != null ? new SimpleStringProperty(p.getValue().getCarType().getName()) : new SimpleStringProperty());
    fromColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleStringProperty(p.getValue().getFrom().toString()) : new SimpleStringProperty());
    toColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleStringProperty(p.getValue().getTo().toString()) : new SimpleStringProperty());
    kwColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleObjectProperty<>(p.getValue().getKw()) : new SimpleObjectProperty<>());
    hpColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleObjectProperty<>((int)((float)p.getValue().getKw() * 1.34102)) : new SimpleObjectProperty<>());
    capacityColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleObjectProperty<>(p.getValue().getCapacity()) : new SimpleObjectProperty<>());
    cilindersColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleObjectProperty<>(p.getValue().getCilinders()) : new SimpleObjectProperty<>());
    enginesColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleObjectProperty<>(p.getValue().getEnginesString()) : new SimpleObjectProperty<>());

    table.getColumns().addAll(nameColumn, modelColumn, fromColumn, toColumn, kwColumn, hpColumn, capacityColumn, cilindersColumn, enginesColumn);
    return table;
  }

  @SuppressWarnings("unchecked")
  public TableView<CarComponentModel> createCarComponentTable() {
    TableView<CarComponentModel> table = new TableView<>();
    TableColumn<CarComponentModel, String> nameColumn = new TableColumn<>("Nume");
    TableColumn<CarComponentModel, String> codeCoulmn = new TableColumn<>("Cod");
    TableColumn<CarComponentModel, String> stockColumn = new TableColumn<>("Stoc");
    TableColumn<CarComponentModel, String> subkitColumn = new TableColumn<>("Sub-ansamblu");

    DoubleBinding widthProperty = table.widthProperty().multiply(.25);
    nameColumn.prefWidthProperty().bind(widthProperty);
    codeCoulmn.prefWidthProperty().bind(widthProperty);
    stockColumn.prefWidthProperty().bind(widthProperty);
    subkitColumn.prefWidthProperty().bind(widthProperty);

    nameColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleStringProperty(p.getValue().getName()) : new SimpleStringProperty());
    codeCoulmn.setCellValueFactory(p -> p.getValue() != null ? new SimpleStringProperty(p.getValue().getCode()) : new SimpleStringProperty());
    stockColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleStringProperty(p.getValue().getStock()) : new SimpleStringProperty());
    subkitColumn.setCellValueFactory(p -> {
      if (p.getValue() != null) {
        CarSubkitModel carSubkit = ModelFilter.getCarSubkitModelById(p.getValue().getCarSubkitId());
        return carSubkit != null ? new SimpleStringProperty(carSubkit.getName()) : new SimpleStringProperty();
      }
      return new SimpleStringProperty();
    });

    nameColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);
    codeCoulmn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);
    stockColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);
    subkitColumn.setStyle(StyleProvider.CENTERED_TABLE_CELL_TEXT_CSS);

    table.getColumns().addAll(nameColumn, codeCoulmn, stockColumn, subkitColumn);
    return table;
  }
}
