package com.autotaller.app.utils;

/**
 * Created by razvanolar on 03.06.2017
 */
public enum StockType {

  DEPOZIT(1, "Depozit"), PARK(2, "Parc");

  int id;
  String name;

  StockType(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }



  public static StockType fromId(int id) {
    return id == PARK.id ? PARK : DEPOZIT;
  }

  @Override
  public String toString() {
    return name;
  }
}
