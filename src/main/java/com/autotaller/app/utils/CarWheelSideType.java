package com.autotaller.app.utils;

/**
 * Created by razvanolar on 16.06.2017
 */
public enum CarWheelSideType {

  LEFT("Stanga"), RIGHT("Dreapta");

  String value;

  CarWheelSideType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static CarWheelSideType fromString(String value) {
    if (value == null)
      return LEFT;
    if (value.equalsIgnoreCase(LEFT.value))
      return LEFT;
    if (value.equalsIgnoreCase(RIGHT.value))
      return RIGHT;
    return LEFT;
  }
}
