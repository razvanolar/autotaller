package com.autotaller.app.components.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 19.04.2017
 */
public class JFXOkCancelDialogLayout extends JFXDialogLayout {

  private JFXButton okButton;
  private JFXButton cancelButton;

  private String confirmText;
  private String cancelText;
  private String title;
  private JFXDialog dialog;

  public JFXOkCancelDialogLayout(String title, String confirmText, String cancelText, JFXDialog dialog) {
    super();
    this.title = title;
    this.confirmText = confirmText;
    this.cancelText = cancelText;
    this.dialog = dialog;
    init();
    setActionNodes();
    addListeners();
  }

  public JFXOkCancelDialogLayout(String title, String confirmText, JFXDialog dialog) {
    this(title, confirmText, "Renunta", dialog);
  }

  protected void init() {
    okButton = new JFXButton(confirmText);
    cancelButton = new JFXButton(cancelText);
    setHeading(new Text(title));
  }

  private void setActionNodes() {
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

  public JFXButton getCancelButton() {
    return cancelButton;
  }
}
