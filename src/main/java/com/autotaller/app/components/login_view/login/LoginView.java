package com.autotaller.app.components.login_view.login;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.utils.MaskableView;
import com.autotaller.app.events.login_view.ReleaseLoginViewEvent;
import com.autotaller.app.events.login_view.ReleaseLoginViewEventHandler;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.MaskViewEventHandler;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEventHandler;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 10.04.2017
 */
public class LoginView extends MaskableView implements LoginController.ILoginView {

  private GridPane gridPane;
  private JFXTextField usernameTextField;
  private JFXPasswordField passwordField;
  private JFXButton loginButton;
  private Text signUpLabel;
  private Text forgotPasswordLabel;

  public LoginView() {
    super();
    init();
    addHandlers();
  }

  private void init() {
    gridPane = new GridPane();
    usernameTextField = NodeProvider.createTextField("Nume Utilizator", true, 350);
    passwordField = NodeProvider.createPasswordField("Parola", true, 350);
    loginButton = NodeProvider.createButton("Autentificare");
    signUpLabel = NodeProvider.createTextLabel("Inregistreaza cont", 15, true);
    forgotPasswordLabel = NodeProvider.createTextLabel("Am uitat parola", 15, true);

    HBox buttonContainer = new HBox(loginButton);
    buttonContainer.setAlignment(Pos.CENTER_RIGHT);

    gridPane.add(NodeProvider.createCenteredNode(new ImageView(ImageProvider.authenticationIcon())), 0, 0);
    gridPane.add(usernameTextField, 0, 2);
    gridPane.add(passwordField, 0, 3);
    gridPane.add(buttonContainer, 0, 4);
    gridPane.add(NodeProvider.createCenteredNode(signUpLabel), 0, 7);
    gridPane.add(NodeProvider.createCenteredNode(forgotPasswordLabel), 0, 8);
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    mainContainer.getChildren().add(gridPane);
  }

  private void addHandlers() {
    EventBus.addHandler(MaskViewEvent.TYPE, (MaskViewEventHandler) event -> maskView(event.getMessage()));

    EventBus.addHandler(UnmaskViewEvent.TYPE, (UnmaskViewEventHandler) event -> unmaskView());

    EventBus.addHandler(ReleaseLoginViewEvent.TYPE, (ReleaseLoginViewEventHandler) event -> {
      EventBus.removeHandlersByType(MaskViewEvent.TYPE);
      EventBus.removeHandlersByType(UnmaskViewEvent.TYPE);
      EventBus.removeHandlersByType(ReleaseLoginViewEvent.TYPE);
    });
  }

  public void showAuthenticationFailedLabel() {
    Text failedAuthenticationLabel = NodeProvider.createErrorTextLabel("Numele de utilizator si/sau parola sunt incorecte", 15);
    gridPane.add(failedAuthenticationLabel, 0, 5);
  }

  public Button getLoginButton() {
    return loginButton;
  }

  public TextField getUsernameTextField() {
    return usernameTextField;
  }

  public TextField getPasswordField() {
    return passwordField;
  }

  public Text getSignUpLabel() {
    return signUpLabel;
  }

  public Text getForgotPasswordLabel() {
    return forgotPasswordLabel;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
