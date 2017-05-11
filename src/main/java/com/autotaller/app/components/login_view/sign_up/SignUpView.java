package com.autotaller.app.components.login_view.sign_up;

import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 12.04.2017
 */
public class SignUpView implements SignUpController.ISignUpView {

  private GridPane gridPane;
  private JFXTextField emplyeeNameTextField;
  private JFXTextField usernameTextField;
  private JFXPasswordField passwordField;
  private JFXPasswordField re_passwordField;
  private Text backLabel;
  private Button signUpButton;

  public SignUpView() {
    init();
  }

  private void init() {
    emplyeeNameTextField = NodeProvider.createMaterialTextField("Nume Angajat", true, 350);
    usernameTextField = NodeProvider.createMaterialTextField("Nume Utilizator", true, 350);
    passwordField = NodeProvider.createPasswordField("Parola", true, 350);
    re_passwordField = NodeProvider.createPasswordField("Retiparire Parola", true, 350);
    backLabel = NodeProvider.createTextLabel("Inapoi", 15, true);
    signUpButton = NodeProvider.createButton("Inregistrare");
    gridPane = new GridPane();

    BorderPane borderPane = new BorderPane();
    borderPane.setLeft(backLabel);
    borderPane.setRight(signUpButton);

    gridPane.add(NodeProvider.createCenteredNode(new ImageView(ImageProvider.signUpIcon())), 0, 0);
    gridPane.add(emplyeeNameTextField, 0, 2);
    gridPane.add(usernameTextField, 0, 3);
    gridPane.add(passwordField, 0, 4);
    gridPane.add(re_passwordField, 0, 5);
    gridPane.add(borderPane, 0, 6);
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setVgap(10);
    gridPane.setHgap(10);

    emplyeeNameTextField.setUnFocusColor(Color.rgb(77, 77, 77));
  }

  public TextField getEmplyeeNameTextField() {
    return emplyeeNameTextField;
  }

  public TextField getUsernameTextField() {
    return usernameTextField;
  }

  public PasswordField getPasswordField() {
    return passwordField;
  }

  public PasswordField getRe_passwordField() {
    return re_passwordField;
  }

  public Text getBackLabel() {
    return backLabel;
  }

  public Button getSignUpButton() {
    return signUpButton;
  }

  @Override
  public Node asNode() {
    return gridPane;
  }
}
