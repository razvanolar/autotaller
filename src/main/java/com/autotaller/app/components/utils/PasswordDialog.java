package com.autotaller.app.components.utils;

import com.autotaller.app.utils.resources.NodeProvider;
import com.jfoenix.controls.JFXDialog;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

/**
 * Created by razvanolar on 04.06.2017
 */
public class PasswordDialog extends JFXDialog {

  private JFXOkCancelDialogLayout dialogLayout;
  private PasswordField passwordField;

  public PasswordDialog(String title) {
    passwordField = NodeProvider.createPasswordField("Parola", true, NodeProvider.DEFAULT_FIELD_WIDTH);
    passwordField.setPadding(new Insets(15));
    dialogLayout = new JFXOkCancelDialogLayout(title, "Ok", this);
    dialogLayout.setBody(passwordField);
    this.setContent(dialogLayout);
    this.setTransitionType(JFXDialog.DialogTransition.CENTER);
  }

  public String getText() {
    return passwordField.getText();
  }

  public Button getConfirmationButton() {
    return dialogLayout.getOkButton();
  }
}
