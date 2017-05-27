package com.autotaller.app.components.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 07.05.2017
 */
public class JFXOkDialogLayout extends JFXDialogLayout {

  private JFXButton okButton;

  private String confirmText;
  private String title;
  private JFXDialog dialog;

  public JFXOkDialogLayout(String confirmText, String title, JFXDialog dialog) {
    super();
    this.confirmText = confirmText;
    this.title = title;
    this.dialog = dialog;
    init();
    setActionNodes();
    addListeners();
  }

  protected void init() {
    okButton = new JFXButton(confirmText);
    setHeading(new Text(title));
  }

  private void setActionNodes() {
    setActions(okButton);
  }

  private void addListeners() {
    okButton.setOnAction(event -> {
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
