package com.autotaller.app.utils;

/**
 * Created by razvanolar on 14.04.2017
 */
public class StringValidator {

  public static boolean isNullOrEmpty(String value) {
    return value == null || value.trim().isEmpty();
  }

  public static boolean isPositiveInteger(String value) {
    return !isNullOrEmpty(value) && value.matches("^\\d+$");
  }

  public static boolean isInteger(String value) {
    return !isNullOrEmpty(value) && value.trim().matches("^[-]?\\d+$");
  }

  public static Integer getValue(String value) {
    return isInteger(value) ? Integer.parseInt(value) : null;
  }
}
