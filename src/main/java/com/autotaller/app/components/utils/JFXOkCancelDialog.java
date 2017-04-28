package com.autotaller.app.components.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 19.04.2017
 */
public class JFXOkCancelDialog extends JFXDialogLayout {

  protected JFXButton okButton;
  protected JFXButton cancelButton;

  private String confirmText;
  private String title;
  private JFXDialog dialog;

  public JFXOkCancelDialog(String title, String confirmText, JFXDialog dialog) {
    super();
    this.title = title;
    this.confirmText = confirmText;
    this.dialog = dialog;
    init();
    setActionNodes();
    addListeners();
  }

  protected void init() {
    okButton = new JFXButton(confirmText);
    cancelButton = new JFXButton("Renunta");
    setHeading(new Text(title));
  }

  protected void setActionNodes() {
    setActions(okButton, cancelButton);
  }

  private void addListeners() {
    cancelButton.setOnAction(event -> {
      if (dialog != null)
        dialog.close();
    });
  }

  public void setBody(Node node) {
    super.setBody(node);
  }

  public JFXButton getOkButton() {
    return okButton;
  }
}
