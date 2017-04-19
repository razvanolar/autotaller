package com.autotaller.app.utils.factories;

import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentTypes;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;

/**
 * Created by razvanolar on 11.04.2017
 */
public class ComponentFactory {

  private static ControllerFactory controllerFactory = new ControllerFactory();
  private static ViewFactory viewFactory = new ViewFactory();

  public static Component createComponent(ComponentTypes type) {
    Controller controller = controllerFactory.createController(type);
    View view = viewFactory.createView(type);
    return controller != null && view != null ? new Component(controller, view) : null;
  }
}
