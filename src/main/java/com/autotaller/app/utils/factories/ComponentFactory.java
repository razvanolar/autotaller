package com.autotaller.app.utils.factories;

import com.autotaller.app.utils.*;
import javafx.scene.control.Button;

/**
 * Created by razvanolar on 11.04.2017
 */
public class ComponentFactory {

  private static ControllerFactory controllerFactory = new ControllerFactory();
  private static ViewFactory viewFactory = new ViewFactory();

  public static Component createComponent(ComponentType type) {
    Controller controller = controllerFactory.createController(type);
    View view = viewFactory.createView(type);
    return controller != null && view != null ? new Component(controller, view) : null;
  }

  public static Component createDialogComponent(DialogComponentType dialogType, Button actionButton) {
    Component component = createComponent(dialogType.getType());
    if (component != null && component.getController() instanceof DialogController) {
      DialogController dialogController = (DialogController) component.getController();
      dialogController.injectActionButton(actionButton);
    }
    return component;
  }
}
