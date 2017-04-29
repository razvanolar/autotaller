package com.autotaller.app.model;

/**
 * Created by razvanolar on 29.04.2017
 */
public class CarKitCategoryModel {

  private int id;
  private String name;

  public CarKitCategoryModel(int id, String name) {
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
