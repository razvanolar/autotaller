package com.autotaller.app.utils.resources;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

  public static HBox createCenteredNode(Node node) {
    HBox hBox = new HBox(node);
    hBox.setAlignment(Pos.CENTER);
    return hBox;
  }

  public static Node createAppMenu(String text, Image icon) {
    return createAppMenu(text, icon, 150, 200);
  }

  public static Node createAppMenu(String text, Image icon, int width, int height) {
    VBox vBox = new VBox();
    Text textLabel = new Text(text);
    textLabel.setFont(new Font(15));
    vBox.getChildren().addAll(textLabel, new ImageView(icon));

    vBox.setAlignment(Pos.CENTER);
    vBox.getStyleClass().add(StyleProvider.APP_MENU_BOX_CLASS);
    vBox.setPrefWidth(width);
    vBox.setPrefHeight(height);
    return vBox;
  }
}
