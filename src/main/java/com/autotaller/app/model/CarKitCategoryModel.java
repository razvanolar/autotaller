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
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof CarKitCategoryModel))
      return false;
    CarKitCategoryModel carKitCategory = (CarKitCategoryModel) obj;
    return this.id == carKitCategory.id;
  }

  @Override
  public String toString() {
    return name;
  }
}
