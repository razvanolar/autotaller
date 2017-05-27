package com.autotaller.app.components.utils;

import com.jfoenix.controls.JFXDialog;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Created by razvanolar on 26.05.2017
 */
public class NodeDialog extends JFXDialog {

  private final JFXOkCancelDialogLayout dialogLayout;

  public NodeDialog(String title, String confirmationText, Node content) {
    dialogLayout = new JFXOkCancelDialogLayout(title, confirmationText, this);
    dialogLayout.setBody(content);
    this.setContent(dialogLayout);
    this.setTransitionType(JFXDialog.DialogTransition.CENTER);
  }

  public Button getConfirmationButton() {
    return dialogLayout.getOkButton();
  }
}
