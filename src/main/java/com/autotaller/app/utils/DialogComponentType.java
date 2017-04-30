package com.autotaller.app.utils;

/**
 * Created by razvanolar on 19.04.2017
 */
public enum DialogComponentType {
  ADD_CAR_MAKE_DIALOG("Adauga Marca", "Adauga", ComponentType.ADD_CAR_MAKE_VIEW),
  ADD_CAR_MODEL_DIALOG("Adauga Model", "Adauga", null),
  ADD_CAR_KIT_DIALOG("Adauga Ansamblu", "Adauga", null);

  String title;
  String actionButtonText;
  ComponentType type;

  DialogComponentType(String title, String actionButtonText, ComponentType type) {
    this.title = title;
    this.actionButtonText = actionButtonText;
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public String getActionButtonText() {
    return actionButtonText;
  }

  public ComponentType getType() {
    return type;
  }
}
