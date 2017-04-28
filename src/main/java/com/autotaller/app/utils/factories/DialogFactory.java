package com.autotaller.app.utils.factories;

import com.autotaller.app.components.utils.JFXOkCancelDialog;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.DialogComponentType;
import com.jfoenix.controls.JFXDialog;

/**
 * Created by razvanolar on 19.04.2017
 */
public class DialogFactory {

  public static JFXDialog createDialog(DialogComponentType type) {
    JFXDialog dialog = new JFXDialog();
    JFXOkCancelDialog dialogView = new JFXOkCancelDialog(type.getTitle(), type.getActionButtonText(), dialog);
    Component component = ComponentFactory.createDialogComponent(type, dialogView.getOkButton());
    if (component != null)
      dialogView.setBody(component.getView().asNode());
    dialog.setContent(dialogView);
    dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
    return dialog;
  }

  public static JFXDialog createDialog(DialogComponentType type, Component component) {
    JFXDialog dialog = new JFXDialog();
    JFXOkCancelDialog dialogView = new JFXOkCancelDialog(type.getTitle(), type.getActionButtonText(), dialog);
    if (component != null)
      dialogView.setBody(component.getView().asNode());
    dialog.setContent(dialogView);
    dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
    return dialog;
  }
}
