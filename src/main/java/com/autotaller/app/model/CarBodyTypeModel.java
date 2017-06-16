package com.autotaller.app.model;

/**
 * Created by razvanolar on 16.06.2017
 */
public class CarBodyTypeModel {

  private int id;
  private String name;

  public CarBodyTypeModel(int id, String name) {
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
