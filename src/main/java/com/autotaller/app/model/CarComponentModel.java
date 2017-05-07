package com.autotaller.app.model;

/**
 * Created by razvanolar on 07.05.2017
 */
public class CarComponentModel {

  private int id;
  private int carModelId;
  private int carSubkitId;
  private String name;
  private String code;
  private String stock;

  public CarComponentModel(int id, int carModelId, int carSubkitId, String name, String code, String stock) {
    this.id = id;
    this.carModelId = carModelId;
    this.carSubkitId = carSubkitId;
    this.name = name;
    this.code = code;
    this.stock = stock;
  }

  public int getId() {
    return id;
  }

  public int getCarModelId() {
    return carModelId;
  }

  public int getCarSubkitId() {
    return carSubkitId;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public String getStock() {
    return stock;
  }
}