package com.autotaller.app.components.utils;

import com.jfoenix.controls.JFXDialog;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 27.05.2017
 */
public class YesNoDialog extends JFXDialog {

  private JFXOkCancelDialogLayout dialogLayout;

  public YesNoDialog(String title, String content) {
    dialogLayout = new JFXOkCancelDialogLayout(title, "Da", "Nu", this);
    dialogLayout.setBody(new Text(content));
    this.setContent(dialogLayout);
    this.setTransitionType(JFXDialog.DialogTransition.CENTER);
  }

  public Button getYesButton() {
    return dialogLayout.getConfirmationButton();
  }

  public Button getNoButton() {
    return dialogLayout.getCancelButton();
  }
}
