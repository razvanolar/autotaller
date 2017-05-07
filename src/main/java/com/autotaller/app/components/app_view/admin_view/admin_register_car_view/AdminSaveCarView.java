package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.*;
import com.autotaller.app.utils.resources.NodeProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 04.05.2017
 */
public class AdminSaveCarView extends IterableView implements AdminSaveCarController.IAdminSaveCarView {

  private SplitPane mainSplitPane;
  private GridPane mainGridPane;
  private SplitPane componentsSplitPane;
  private ScrollPane componentsScrollPane;
  private TableView<CarComponentModel> componentsTable;

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

  private ComboBox<CarKitCategoryModel> carKitCategoryCombo;
  private ComboBox<CarKitModel> carKitCombo;
  private TextField componentNameTextField;
  private TextField componentCodeTextField;
  private TextField componentStockTextField;
  private TextArea componentDescriptionTextArea;
  private TextField componentImageTextField;
  private Button componentImageAddButton;
  private Button componentAddButton;

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
    mainGridPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    mainGridPane.setPadding(new Insets(10, 0, 0, 0));
    mainGridPane.add(createCategoryHBox("Atribute Masina"), 0, row++, 2, 1);
    mainGridPane.add(NodeProvider.createFormTextLabel("*Marca: "), 0, row);
    mainGridPane.add(carMakesCombo, 1, row++);
    mainGridPane.add(NodeProvider.createFormTextLabel("*Model: "), 0, row);
    mainGridPane.add(carTypesCombo, 1, row++);
    mainGridPane.add(NodeProvider.createFormTextLabel("*Nume: "), 0, row);
    mainGridPane.add(carNameField, 1, row++);
    mainGridPane.add(NodeProvider.createFormTextLabel("*De la: "), 0, row);
    mainGridPane.add(fromDatePicker, 1, row++);
    mainGridPane.add(NodeProvider.createFormTextLabel("*Pana la: "), 0, row);
    mainGridPane.add(toDatePicker, 1, row++);
    mainGridPane.add(NodeProvider.createFormTextLabel("*KW: "), 0, row);
    mainGridPane.add(kwSpinner, 1, row++);
    mainGridPane.add(NodeProvider.createFormTextLabel("*Capacitate: "), 0, row);
    mainGridPane.add(capCilSpinner, 1, row++);
    mainGridPane.add(NodeProvider.createFormTextLabel("*Cilindrii: "), 0, row);
    mainGridPane.add(cilindersSpinner, 1, row++);
    mainGridPane.add(NodeProvider.createFormTextLabel("*Cod motor: "), 0, row);
    mainGridPane.add(engineField, 1, row++);
    mainGridPane.add(NodeProvider.createFormTextLabel("*Combustibil: "), 0, row);
    mainGridPane.add(fuelCombo, 1, row++);
    mainGridPane.add(addComponentsLabelContainer, 0, row, 2, 1);

    mainSplitPane = new SplitPane(mainGridPane);
    borderPane.setCenter(mainSplitPane);

    carKitCategoryCombo = NodeProvider.createCarKitCategoriesCombo(NodeProvider.DEFAULT_FIELD_WIDTH);
    carKitCombo = NodeProvider.createCarKitCombo(NodeProvider.DEFAULT_FIELD_WIDTH);
    componentNameTextField = NodeProvider.createTextField();
    componentCodeTextField = NodeProvider.createTextField();
    componentStockTextField = NodeProvider.createTextField();
    componentDescriptionTextArea = new TextArea();
    componentImageTextField = NodeProvider.createTextField();
    componentImageAddButton = NodeProvider.createButton("Adauga Imagine");
    componentAddButton = NodeProvider.createButton("Adauga Componenta");

    componentDescriptionTextArea.setPrefSize(340, 80);

    GridPane componentsGridPane = NodeProvider.createGridPane(Pos.TOP_CENTER, 10, 10);
    componentsGridPane.setPadding(new Insets(10, 0, 0, 0));
    row = 0;
    // add component attribute fields
    componentsGridPane.add(createCategoryHBox("Atribute Componenta"), 0, row++, 2, 1);
    componentsGridPane.add(NodeProvider.createFormTextLabel("Categorie Ans.: "), 0, row);
    componentsGridPane.add(carKitCategoryCombo, 1, row++);
    componentsGridPane.add(NodeProvider.createFormTextLabel("*Ansamblu: "), 0, row);
    componentsGridPane.add(carKitCombo, 1, row++);
    componentsGridPane.add(NodeProvider.createFormTextLabel("*Nume: "), 0, row);
    componentsGridPane.add(componentNameTextField, 1, row++);
    componentsGridPane.add(NodeProvider.createFormTextLabel("*Cod: "), 0, row);
    componentsGridPane.add(componentCodeTextField, 1, row++);
    componentsGridPane.add(NodeProvider.createFormTextLabel("*Stoc: "), 0, row);
    componentsGridPane.add(componentStockTextField, 1, row++);
    componentsGridPane.add(createCategoryHBox("Descriere Componenta"), 0, row++, 2, 1);
    componentsGridPane.add(componentDescriptionTextArea, 0, row, 2, 1);
    // add component image fields
    componentsGridPane.add(createCategoryHBox("Imagini"), 3, 0, 2,1);
    componentsGridPane.add(componentImageTextField, 3, 1);
    componentsGridPane.add(componentImageAddButton, 4, 1);
    // add 'Add' component button
    HBox addComponentButtonContainer = new HBox(componentAddButton);
    addComponentButtonContainer.setAlignment(Pos.BOTTOM_CENTER);
    componentAddButton.prefWidthProperty().bind(addComponentButtonContainer.widthProperty());
    componentsGridPane.add(addComponentButtonContainer, 3, row, 2, 1);

    componentsScrollPane = NodeProvider.createScrollPane(componentsGridPane, true);

    componentsTable = NodeProvider.createCarComponentTable();
    componentsSplitPane = new SplitPane(componentsScrollPane, componentsTable);
    componentsSplitPane.setOrientation(Orientation.VERTICAL);
  }

  @Override
  public void showComponentsView() {
    ObservableList<Node> items = mainSplitPane.getItems();
    if (!items.contains(componentsSplitPane)) {
      items.add(componentsSplitPane);
      mainSplitPane.setDividerPosition(0, lastDividerPosition);
      mainGridPane.setAlignment(Pos.TOP_CENTER);
    }
  }

  @Override
  public void hideComponentsView() {
    ObservableList<Node> items = mainSplitPane.getItems();
    if (items.contains(componentsSplitPane)) {
      double[] dividers = mainSplitPane.getDividerPositions();
      if (dividers != null && dividers.length > 0) {
        lastDividerPosition = dividers[0];
      }
      items.remove(componentsSplitPane);
      mainGridPane.setAlignment(Pos.CENTER);
    }
  }

  private HBox createCategoryHBox(String name) {
    HBox hBox = new HBox();
    hBox.setAlignment(Pos.CENTER_LEFT);
    hBox.getChildren().add(NodeProvider.createTextLabel(name, 23, false));
    hBox.getStyleClass().add(StyleProvider.ADMIN_SUB_TOOLBAR_PANE_CLASS);
    return hBox;
  }

  public ComboBox<CarMakeModel> getCarMakesCombo() {
    return carMakesCombo;
  }

  public ComboBox<CarTypeModel> getCarTypesCombo() {
    return carTypesCombo;
  }

  public ComboBox<FuelModel> getFuelCombo() {
    return fuelCombo;
  }

  public ComboBox<CarKitCategoryModel> getCarKitCategoryCombo() {
    return carKitCategoryCombo;
  }

  public ComboBox<CarKitModel> getCarKitCombo() {
    return carKitCombo;
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

  public TableView<CarComponentModel> getComponentsTable() {
    return componentsTable;
  }

  public TextField getComponentNameTextField() {
    return componentNameTextField;
  }

  public TextField getComponentCodeTextField() {
    return componentCodeTextField;
  }

  public TextField getComponentStockTextField() {
    return componentStockTextField;
  }

  public TextArea getComponentDescriptionTextArea() {
    return componentDescriptionTextArea;
  }

  public TextField getComponentImageTextField() {
    return componentImageTextField;
  }

  public Button getComponentImageAddButton() {
    return componentImageAddButton;
  }

  public Button getComponentAddButton() {
    return componentAddButton;
  }

  @Override
  public Node asNode() {
    return borderPane;
  }
}
