package com.autotaller.app.model;

/**
 * Created by razvanolar on 04.05.2017
 */
public class FuelModel {

  private int id;
  private String name;

  public FuelModel(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }
}
