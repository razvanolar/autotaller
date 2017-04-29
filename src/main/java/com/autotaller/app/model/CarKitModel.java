package com.autotaller.app.model;

/**
 * Created by razvanolar on 29.04.2017
 */
public class CarKitModel {

  private int id;
  private CarKitCategoryModel kitCategory;
  private String name;

  public CarKitModel(int id, String name, CarKitCategoryModel kitCategory) {
    this.id = id;
    this.name = name;
    this.kitCategory = kitCategory;
  }

  public int getId() {
    return id;
  }

  public CarKitCategoryModel getKitCategory() {
    return kitCategory;
  }

  public String getName() {
    return name;
  }
}
