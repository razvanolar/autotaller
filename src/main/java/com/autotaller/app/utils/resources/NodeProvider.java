package com.autotaller.app.utils.resources;

import com.autotaller.app.model.CarTypeModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 12.04.2017
 */
public class NodeProvider {

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

  public static JFXTextField createTextField(String promptText, boolean labelFloat, int minWidth) {
    JFXTextField textField = new JFXTextField();
    textField.setPromptText(promptText);
    textField.setLabelFloat(labelFloat);
    textField.setMinWidth(minWidth);
    return textField;
  }

  public static JFXPasswordField createPasswordField(String promptText, boolean labelFloat, int minWidth) {
    JFXPasswordField textField = new JFXPasswordField();
    textField.setPromptText(promptText);
    textField.setLabelFloat(labelFloat);
    textField.setMinWidth(minWidth);
    return textField;
  }

  public static Text createTextLabel(String text, boolean isLink) {
    return createTextLabel(text, -1, isLink);
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

  public static HBox createCenteredNode(Node node) {
    HBox hBox = new HBox(node);
    hBox.setAlignment(Pos.CENTER);
    return hBox;
  }

  public static Node createAppMenu(String text, Image icon) {
    return createAppMenu(text, icon, 150, 200, StyleProvider.APP_MENU_BOX_CLASS);
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

  @SuppressWarnings("unchecked")
  public static TableView<CarTypeModel> createCarModelTable() {
    TableView<CarTypeModel> table = new TableView<>();
    TableColumn<CarTypeModel, String> nameColumn = new TableColumn<>("Nume Model");
    TableColumn<CarTypeModel, String> fromColumn = new TableColumn<>("De la");
    TableColumn<CarTypeModel, String> toColumn = new TableColumn<>("Pana la");
    TableColumn<CarTypeModel, String> makeColumn = new TableColumn<>("Marca");

    nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(.33));
    fromColumn.prefWidthProperty().bind(table.widthProperty().multiply(.17));
    toColumn.prefWidthProperty().bind(table.widthProperty().multiply(.17));
    makeColumn.prefWidthProperty().bind(table.widthProperty().multiply(.33));

    nameColumn.setCellValueFactory(p -> p.getValue() != null ? new SimpleStringProperty(p.getValue().getName()) : new SimpleStringProperty());
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
}
