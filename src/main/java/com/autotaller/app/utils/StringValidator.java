package com.autotaller.app.utils;

/**
 * Created by razvanolar on 14.04.2017
 */
public class StringValidator {

  public static boolean isNullOrEmpty(String value) {
    return value == null || value.trim().isEmpty();
  }
}
