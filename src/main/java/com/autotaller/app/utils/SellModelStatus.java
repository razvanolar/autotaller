package com.autotaller.app.utils;

/**
 * Created by razvanolar on 07.06.2017
 */
public enum  SellModelStatus {

  ALL(-1), UNSOLVED(0), SOLVED(1);

  private int value;

  SellModelStatus(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
