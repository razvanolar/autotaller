package com.autotaller.app.utils.factories;

import com.autotaller.app.components.utils.JFXOkCancelDialogLayout;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.DialogComponentType;
import com.autotaller.app.utils.DialogController;
import com.jfoenix.controls.JFXDialog;

/**
 * Created by razvanolar on 19.04.2017
 */
public class DialogFactory {

  public static JFXDialog createDialog(DialogComponentType type) {
    JFXDialog dialog = new JFXDialog();
    JFXOkCancelDialogLayout dialogView = new JFXOkCancelDialogLayout(type.getTitle(), type.getActionButtonText(), dialog);
    Component component = ComponentFactory.createDialogComponent(type, dialogView.getConfirmationButton());
    if (component != null)
      dialogView.setBody(component.getView().asNode());
    dialog.setContent(dialogView);
    dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
    return dialog;
  }

  public static JFXDialog createDialog(DialogComponentType type, Component component) {
    JFXDialog dialog = new JFXDialog();
    JFXOkCancelDialogLayout dialogView = new JFXOkCancelDialogLayout(type.getTitle(), type.getActionButtonText(), dialog);
    if (component != null) {
      dialogView.setBody(component.getView().asNode());
      if (component.getController() instanceof DialogController) {
        DialogController controller = (DialogController) component.getController();
        controller.injectActionButton(dialogView.getConfirmationButton());
      }
    }
    dialog.setContent(dialogView);
    dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
    return dialog;
  }
}
