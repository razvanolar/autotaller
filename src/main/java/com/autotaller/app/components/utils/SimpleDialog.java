package com.autotaller.app.components.utils;

import com.jfoenix.controls.JFXDialog;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 07.05.2017
 */
public class SimpleDialog extends JFXDialog {

  public SimpleDialog(String title, String confirmationText, String content) {
    JFXOkDialogLayout dialogView = new JFXOkDialogLayout(confirmationText, title , this);
    dialogView.setBody(new Text(content));
    this.setContent(dialogView);
    this.setTransitionType(JFXDialog.DialogTransition.CENTER);
  }
}
