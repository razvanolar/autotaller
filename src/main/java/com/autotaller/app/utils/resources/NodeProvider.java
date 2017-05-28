package com.autotaller.app.utils.resources;

import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.model.*;
import com.jfoenix.controls.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 12.04.2017
 */
public class NodeProvider {

  public static TableProvider TABLE_PROVIDER = new TableProvider();

  public static int DEFAULT_FIELD_WIDTH = 220;

  public static Button createButton(String text) {
    JFXButton button = new JFXButton(text);
    button.getStyleClass().add(StyleProvider.DEFAULT_BUTTON_CLASS);
    return button;
  }

  public static Button createRemoveButton(String text) {
    JFXButton button = new JFXButton(text);
    button.getStyleClass().add(StyleProvider.REMOVE_BUTTON_CLASS);
    return button;
  }

  public static Button createToolbarButton(String text, Image image) {
    Button button = new Button(text);
    if (image != null)
      button.setGraphic(new ImageView(image));
    return button;
  }

  public static ToggleButton createToolbarToggleButton(String text, Image image) {
    ToggleButton button = new ToggleButton(text);
    if (image != null)
      button.setGraphic(new ImageView(image));
    return button;
  }

  public static Button createAdminToolbarButton(String text) {
    Button button = new Button(text);

    return button;
  }

  public static JFXTextField createMaterialTextField(String promptText, boolean labelFloat, int minWidth) {
    JFXTextField textField = new JFXTextField();
    textField.setPromptText(promptText);
    textField.setLabelFloat(labelFloat);
    textField.setMinWidth(minWidth);
    return textField;
  }

  public static TextField createTextField() {
    return createTextField(DEFAULT_FIELD_WIDTH);
  }

  public static TextField createTextField(int width) {
    TextField textField = new TextField();
    textField.setPrefWidth(width);
    return textField;
  }

  public static TextArea createTextArea(int width, int height) {
    TextArea textArea = new TextArea();
    textArea.setPrefSize(width, height);
    return textArea;
  }

  public static JFXPasswordField createPasswordField(String promptText, boolean labelFloat, int minWidth) {
    JFXPasswordField textField = new JFXPasswordField();
    textField.setPromptText(promptText);
    textField.setLabelFloat(labelFloat);
    textField.setMinWidth(minWidth);
    return textField;
  }

  public static Spinner<Integer> createSpinner(int min, int max, int init, int step) {
    return createSpinner(DEFAULT_FIELD_WIDTH, min, max, init, step);
  }

  public static Spinner<Integer> createSpinner(int width, int min, int max, int init, int step) {
    Spinner<Integer> spinner = new Spinner<>(min, max, init, step);
    spinner.setPrefWidth(width);
    spinner.setEditable(true);
    return spinner;
  }

  public static ComboBox<CarMakeModel> createCarMakesCombo() {
    return createCarMakesCombo(DEFAULT_FIELD_WIDTH);
  }

  public static ComboBox<CarMakeModel> createCarMakesCombo(int width) {
    ComboBox<CarMakeModel> combo = new ComboBox<>();
    combo.setPrefWidth(width);
    return combo;
  }

  public static ComboBox<CarTypeModel> createCarTypesCombo() {
    return createCarTypesCombo(DEFAULT_FIELD_WIDTH);
  }

  public static ComboBox<CarTypeModel> createCarTypesCombo(int width) {
    ComboBox<CarTypeModel> combo = new ComboBox<>();
    combo.setPrefWidth(width);
    return combo;
  }

  public static ComboBox<CarKitCategoryModel> createCarKitCategoriesCombo(int width) {
    ComboBox<CarKitCategoryModel> combo = new ComboBox<>();
    combo.setPrefWidth(width);
    return combo;
  }

  public static ComboBox<CarKitModel> createCarKitCombo(int width) {
    ComboBox<CarKitModel> combo = new ComboBox<>();
    combo.setPrefWidth(width);
    return combo;
  }

  public static ComboBox<CarSubkitModel> createCarSubkitCombo(int width) {
    ComboBox<CarSubkitModel> combo = new ComboBox<>();
    combo.setPrefWidth(width);
    return combo;
  }

  public static ComboBox<FuelModel> createFuelCombo() {
    return createFuelCombo(DEFAULT_FIELD_WIDTH);
  }

  public static ComboBox<FuelModel> createFuelCombo(int width) {
    ComboBox<FuelModel> combo = new ComboBox<>();
    combo.setPrefWidth(width);
    return combo;
  }

  public static Text createFormTextLabel(String text) {
    return createTextLabel(text, 13, false);
  }

  public static Text createErrorTextLabel(String text, int fontSize) {
    Text textLabel = new Text(text);
    if (fontSize >= 0)
      textLabel.setFont(new Font(15));
    textLabel.getStyleClass().add(StyleProvider.ERROR_TEXT_COLOR_CLASS);
    return textLabel;
  }

  public static Text createTextLabel(String text, int fontSize, boolean isLink) {
    Text textLabel = new Text(text);
    if (fontSize >= 0)
      textLabel.setFont(new Font(fontSize));
    textLabel.getStyleClass().add(StyleProvider.DEFAULT_TEXT_COLOR_CLASS);
    if (isLink)
      textLabel.getStyleClass().add(StyleProvider.DEFAULT_LINK_HOVER_CLASS);
    return textLabel;
  }

  public static ToolBar createToolBar() {
    ToolBar toolBar = new ToolBar();
    toolBar.getStyleClass().add(StyleProvider.BUTTONS_CONTAINER_CLASS);
    return toolBar;
  }

  public static DatePicker createDatePicker() {
    return createDatePicker(DEFAULT_FIELD_WIDTH);
  }

  public static DatePicker createDatePicker(int width) {
    DatePicker datePicker = new DatePicker();
    datePicker.setPrefWidth(width);
    return datePicker;
  }

  public static HBox createCenteredNode(Node node) {
    HBox hBox = new HBox(node);
    hBox.setAlignment(Pos.CENTER);
    return hBox;
  }

  public static VBox createTitlePane(String title) {
    VBox node = new VBox();
    FillToolItem bar = new FillToolItem();
    bar.setPrefHeight(5);
    bar.getStyleClass().add(StyleProvider.TITLE_PANE_CLASS);
    node.getChildren().addAll(createTextLabel(title, 17, false), bar);
    node.setAlignment(Pos.CENTER_LEFT);
    node.setPadding(new Insets(5, 10, 5, 10));
    return node;
  }

  public static VBox createVBox(int spacing) {
    VBox node = new VBox(spacing);
    node.getStyleClass().add(StyleProvider.GENERAL_PANE_BACKGROUND_CLASS);
    return node;
  }

  public static VBox createVBox(int spacing, Node... nodes) {
    VBox node = createVBox(spacing);
    if (nodes != null)
      node.getChildren().addAll(nodes);
    return node;
  }

  public static VBox createVBox(Pos pos, int spacing, Node... nodes) {
    VBox node = createVBox(spacing, nodes);
    if (pos != null)
      node.setAlignment(pos);
    return node;
  }

  public static BorderPane createBorderPane() {
    BorderPane pane = new BorderPane();
    pane.getStyleClass().add(StyleProvider.GENERAL_PANE_BACKGROUND_CLASS);
    return pane;
  }

  public static FlowPane createFlowPane(Pos pos) {
    FlowPane flowPane = new FlowPane();
    flowPane.getStyleClass().add(StyleProvider.GENERAL_PANE_BACKGROUND_CLASS);
    flowPane.setAlignment(pos);
    return flowPane;
  }

  public static Node createAppMenu(String text, Image icon) {
    return createAppMenu(text, icon, 150, 200, StyleProvider.APP_MENU_BOX_CLASS);
  }

  public static Node createAdminAppMenu(String text, Image icon) {
    return createAppMenu(text, icon, 500, 50, StyleProvider.APP_MENU_BOX_CLASS);
  }

  private static Node createAppMenu(String text, Image icon, int width, int height, String style) {
    VBox vBox = new VBox();
    Text textLabel = new Text(text);
    textLabel.setFont(new Font(15));
    vBox.getChildren().add(textLabel);
    if (icon != null)
      vBox.getChildren().add(new ImageView(icon));

    vBox.setAlignment(Pos.CENTER);
    vBox.getStyleClass().add(style);
    vBox.setPrefWidth(width);
    vBox.setPrefHeight(height);
    return vBox;
  }

  public static Node createCarMakeMenu(String text, Image icon, int width, int height) {
    return createAppMenu(text, icon, width, height, StyleProvider.DEFAULT_BUTTON_CLASS);
  }

  public static ScrollPane createScrollPane(Region content) {
    ScrollPane scrollPane = new ScrollPane(content) {public void requestFocus() {}};
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.getStyleClass().add(StyleProvider.GENERAL_SCROLL_PANE_BACKGROUND_CLASS);
    return scrollPane;
  }

  public static ScrollPane createScrollPane(Region content, boolean centerContent) {
    ScrollPane scroll = createScrollPane(content);
    scroll.setFitToWidth(centerContent);
    scroll.setFitToHeight(centerContent);
    return scroll;
  }

  public static GridPane createGridPane(Pos pos, int hGap, int vGap) {
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(pos);
    gridPane.setHgap(hGap);
    gridPane.setVgap(vGap);
    gridPane.getStyleClass().add(StyleProvider.GENERAL_PANE_BACKGROUND_CLASS);
    return gridPane;
  }

  public static TableView<CarMakeModel> createCarMakeTable() {
    return TABLE_PROVIDER.createCarMakeTable();
  }

  public static TableView<CarTypeModel> createCarModelTable() {
    return TABLE_PROVIDER.createCarModelTable();
  }

  public static TableView<CarKitModel> createCarKitModelTable() {
    return TABLE_PROVIDER.createCarKitModelTable();
  }

  public static TableView<CarSubkitModel> createCarSubkitModelTable() {
    return TABLE_PROVIDER.createCarSubkitModelTable();
  }

  public static TableView<CarModel> createCarTable() {
    return TABLE_PROVIDER.createCarTable();
  }

  public static TableView<CarComponentModel> createCarComponentTable() {
    return createCarComponentTable(false);
  }

  public static TableView<CarComponentModel> createCarComponentTable(boolean isEditable) {
    return TABLE_PROVIDER.createCarComponentTable(isEditable);
  }

  public static TableView<CarComponentModel> createCarComponentValidatorTable() {
    return TABLE_PROVIDER.createCarComponentValidationTable();
  }
}
