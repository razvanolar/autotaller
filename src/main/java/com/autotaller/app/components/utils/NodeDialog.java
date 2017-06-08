package com.autotaller.app.components.utils;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Created by razvanolar on 26.05.2017
 */
public class NodeDialog extends JFXDialog {

  private IJFXOkDialogLayout dialogLayout;

  public NodeDialog(String title, String confirmationText, Node content) {
    this(title, confirmationText, content, true);
  }

  public NodeDialog(String title, String confirmationText, Node content, boolean isOkCancelDialog) {
    dialogLayout = isOkCancelDialog ? new JFXOkCancelDialogLayout(title, confirmationText, this) : new JFXOkDialogLayout(confirmationText, title, this);
    JFXDialogLayout layout = (JFXDialogLayout) dialogLayout;
    layout.setBody(content);
    this.setContent(layout);
    this.setTransitionType(JFXDialog.DialogTransition.CENTER);
  }

  public Button getConfirmationButton() {
    return dialogLayout.getConfirmationButton();
  }
}
