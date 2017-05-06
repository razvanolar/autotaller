package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.*;
import com.autotaller.app.utils.resources.NodeProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Created by razvanolar on 04.05.2017
 */
public class AdminSaveCarView extends IterableView implements AdminSaveCarController.IAdminSaveCarView {

  private SplitPane splitPane;
  private GridPane gridPane;

  private GridPane filterPane;
  private ScrollPane componentsScrollPane;
  private ComboBox<CarKitCategoryModel> carKitCategoryCombo;
  private ComboBox<CarKitModel> carKitCombo;

  private ComboBox<CarMakeModel> carMakesCombo;
  private ComboBox<CarTypeModel> carTypesCombo;
  private ComboBox<FuelModel> fuelCombo;
  private TextField carNameField;
  private DatePicker fromDatePicker;
  private DatePicker toDatePicker;
  private Spinner<Integer> kwSpinner;
  private Spinner<Integer> capCilSpinner;
  private Spinner<Integer> cilindersSpinner;
  private TextField engineField;
  private Text addComponentsLink;

  private Button saveCarButton;

  private double lastDividerPosition = .3;

  public AdminSaveCarView() {
    init();
  }

  private void init() {
    carMakesCombo = NodeProvider.createCarMakesCombo();
    carTypesCombo = NodeProvider.createCarTypesCombo();
    fuelCombo = NodeProvider.createFuelCombo();
    carNameField = NodeProvider.createTextField();
    fromDatePicker = NodeProvider.createDatePicker();
    toDatePicker = NodeProvider.createDatePicker();
    kwSpinner = NodeProvider.createSpinner(1, 1000, 50, 1);
    capCilSpinner = NodeProvider.createSpinner(1, 10000, 1000, 1);
    cilindersSpinner = NodeProvider.createSpinner(1, 50, 4, 1);
    engineField = NodeProvider.createTextField();
    addComponentsLink = NodeProvider.createTextLabel("Adauga Componente", 16, true);
    saveCarButton = new Button("Salveaza");

    toolBar.getItems().addAll(new Separator(), saveCarButton);

    HBox addComponentsLabelContainer = new HBox(addComponentsLink);
    addComponentsLabelContainer.setAlignment(Pos.CENTER_RIGHT);

    int row = 0;
    gridPane = NodeProvider.createGridPane(Pos.CENTER, 10,10);
    gridPane.add(createCategoryHBox("Atribute Masina"), 0, row++, 2, 1);
    gridPane.add(NodeProvider.createFormTextLabel("Marca: "), 0, row);
    gridPane.add(carMakesCombo, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Model: "), 0, row);
    gridPane.add(carTypesCombo, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Nume: "), 0, row);
    gridPane.add(carNameField, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("De la: "), 0, row);
    gridPane.add(fromDatePicker, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Pana la: "), 0, row);
    gridPane.add(toDatePicker, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("KW: "), 0, row);
    gridPane.add(kwSpinner, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Capacitate: "), 0, row);
    gridPane.add(capCilSpinner, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Cilindrii: "), 0, row);
    gridPane.add(cilindersSpinner, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Cod motor: "), 0, row);
    gridPane.add(engineField, 1, row++);
    gridPane.add(NodeProvider.createFormTextLabel("Combustibil: "), 0, row);
    gridPane.add(fuelCombo, 1, row++);
    gridPane.add(addComponentsLabelContainer, 0, row, 2, 1);

    splitPane = new SplitPane(gridPane);
    borderPane.setCenter(splitPane);

    carKitCategoryCombo = NodeProvider.createCarKitCategoriesCombo(NodeProvider.DEFAULT_FIELD_WIDTH);
    carKitCombo = NodeProvider.createCarKitCombo(NodeProvider.DEFAULT_FIELD_WIDTH);
    filterPane = NodeProvider.createGridPane(Pos.TOP_CENTER, 10, 10);
    filterPane.setPadding(new Insets(20, 0, 0, 0));
    componentsScrollPane = NodeProvider.createScrollPane(filterPane, true);
  }

  @Override
  public void showComponentsView() {
    ObservableList<Node> items = splitPane.getItems();
    if (!items.contains(componentsScrollPane)) {
      items.add(componentsScrollPane);
      splitPane.setDividerPosition(0, lastDividerPosition);
    }
  }

  @Override
  public void hideComponentsView() {
    ObservableList<Node> items = splitPane.getItems();
    if (items.contains(componentsScrollPane)) {
      double[] dividers = splitPane.getDividerPositions();
      if (dividers != null && dividers.length > 0) {
        lastDividerPosition = dividers[0];
      }
      items.remove(componentsScrollPane);
    }
  }

  public void updateFilterPane(List<AdminSaveCarController.ComponentRowWrapper> componentRowWrappers) {
    filterPane.getChildren().clear();
    filterPane.add(NodeProvider.createFormTextLabel("Categorie: "), 0, 0);
    filterPane.add(carKitCategoryCombo, 1, 0);
    filterPane.add(NodeProvider.createFormTextLabel("Ansamblu: "), 2, 0);
    filterPane.add(carKitCombo, 3, 0);
    if (componentRowWrappers == null || componentRowWrappers.isEmpty())
      return;
    int row = 1;
    for (AdminSaveCarController.ComponentRowWrapper wrapper : componentRowWrappers) {
      CarSubkitModel subkit = wrapper.getSubkit();
      filterPane.add(createFilterFieldHeader(subkit.getCarKit().getName(), subkit.getName()), 0, row, 6, 1);
      row++;
      filterPane.add(NodeProvider.createFormTextLabel("Denumire: "), 0, row);
      filterPane.add(wrapper.getNameField(), 1, row);
      filterPane.add(NodeProvider.createFormTextLabel("Cod: "), 2, row);
      filterPane.add(wrapper.getCodeField(), 3, row);
      filterPane.add(NodeProvider.createFormTextLabel("Stoc: "), 4, row);
      filterPane.add(wrapper.getStockField(), 5, row);
      row++;
    }
  }

  private HBox createFilterFieldHeader(String category, String subCategory) {
    HBox hBox = new HBox();
    hBox.setAlignment(Pos.CENTER_LEFT);
    hBox.getChildren().add(NodeProvider.createTextLabel(category + " -> " + subCategory, 16, false));
    hBox.getStyleClass().add(StyleProvider.ADMIN_SUB_TOOLBAR_PANE_CLASS);
    return hBox;
  }

  private HBox createCategoryHBox(String name) {
    HBox hBox = new HBox();
    hBox.setAlignment(Pos.CENTER_LEFT);
    hBox.getChildren().add(NodeProvider.createTextLabel(name, 23, false));
    return hBox;
  }

  public ComboBox<CarMakeModel> getCarMakesCombo() {
    return carMakesCombo;
  }

  public ComboBox<CarTypeModel> getCarTypesCombo() {
    return carTypesCombo;
  }

  public TextField getCarNameField() {
    return carNameField;
  }

  public DatePicker getFromDatePicker() {
    return fromDatePicker;
  }

  public DatePicker getToDatePicker() {
    return toDatePicker;
  }

  public Spinner<Integer> getKwSpinner() {
    return kwSpinner;
  }

  public Spinner<Integer> getCapCilSpinner() {
    return capCilSpinner;
  }

  public Spinner<Integer> getCilindersSpinner() {
    return cilindersSpinner;
  }

  public TextField getEngineField() {
    return engineField;
  }

  public Text getAddComponentsLink() {
    return addComponentsLink;
  }

  @Override
  public Node asNode() {
    return borderPane;
  }
}
