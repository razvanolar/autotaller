package com.autotaller.app.utils.resources;

import com.autotaller.app.model.*;
import com.jfoenix.controls.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 12.04.2017
 */
public class NodeProvider {

  public static TableProvider TABLE_PROVIDER = new TableProvider();

  public static int DEFAULT_FIELD_WIDTH = 220;

  public static JFXButton createButton(String text) {
    JFXButton button = new JFXButton(text);
    button.getStyleClass().add(StyleProvider.DEFAULT_BUTTON_CLASS);
    return button;
  }

  public static JFXButton createRemoveButton(String text) {
    JFXButton button = new JFXButton(text);
    button.getStyleClass().add(StyleProvider.REMOVE_BUTTON_CLASS);
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

  public static JFXComboBox<CarMakeModel> createCarMakesCombo() {
    return createCarMakesCombo(DEFAULT_FIELD_WIDTH);
  }

  public static JFXComboBox<CarMakeModel> createCarMakesCombo(int width) {
    JFXComboBox<CarMakeModel> combo = new JFXComboBox<>();
    combo.setPrefWidth(width);
    return combo;
  }

  public static JFXComboBox<CarTypeModel> createCarTypesCombo() {
    return createCarTypesCombo(DEFAULT_FIELD_WIDTH);
  }

  public static JFXComboBox<CarTypeModel> createCarTypesCombo(int width) {
    JFXComboBox<CarTypeModel> combo = new JFXComboBox<>();
    combo.setPrefWidth(width);
    return combo;
  }

  public static JFXComboBox<CarKitCategoryModel> createCarKitCategoriesCombo(int width) {
    JFXComboBox<CarKitCategoryModel> combo = new JFXComboBox<>();
    combo.setPrefWidth(width);
    return combo;
  }

  public static JFXComboBox<CarKitModel> createCarKitCombo(int width) {
    JFXComboBox<CarKitModel> combo = new JFXComboBox<>();
    combo.setPrefWidth(width);
    return combo;
  }

  public static JFXComboBox<CarSubkitModel> createCarSubkitCombo(int width) {
    JFXComboBox<CarSubkitModel> combo = new JFXComboBox<>();
    combo.setPrefWidth(width);
    return combo;
  }

  public static ComboBox<FuelModel> createFuelCombo() {
    return createFuelCombo(DEFAULT_FIELD_WIDTH);
  }

  public static ComboBox<FuelModel> createFuelCombo(int width) {
    JFXComboBox<FuelModel> combo = new JFXComboBox<>();
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
      textLabel.setFont(new Font(15));
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
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    return scrollPane;
  }

  public static ScrollPane createScrollPane(Region content, boolean centerContent) {
    ScrollPane scroll = createScrollPane(content);
    if (centerContent) {
      content.minWidthProperty().bind(Bindings.createDoubleBinding(() -> scroll.getViewportBounds().getWidth(), scroll.viewportBoundsProperty()));
      content.minHeightProperty().bind(Bindings.createDoubleBinding(() -> scroll.getViewportBounds().getHeight(), scroll.viewportBoundsProperty()));
    }
    return scroll;
  }

  public static GridPane createGridPane(Pos pos, int hGap, int vGap) {
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(pos);
    gridPane.setHgap(hGap);
    gridPane.setVgap(vGap);
    return gridPane;
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
    return TABLE_PROVIDER.createCarComponentTable();
  }
}
