package com.autotaller.app.utils;

/**
 * Created by razvanolar on 29.12.2016
 */
public class Component {

  private Controller controller;
  private View view;

  @SuppressWarnings("unchecked")
  public Component(Controller controller, View view) {
    this.controller = controller;
    this.view = view;
    this.controller.bind(this.view);
  }

  public Controller getController() {
    return controller;
  }

  public View getView() {
    return view;
  }
}
