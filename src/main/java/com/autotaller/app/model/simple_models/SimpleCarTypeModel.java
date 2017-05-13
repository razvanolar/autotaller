package com.autotaller.app.model.simple_models;

/**
 * Created by razvanolar on 13.05.2017
 */
public class SimpleCarTypeModel {

  private int carTypeId;
  private int carMakeId;
  private String carTypeName;

  public SimpleCarTypeModel(int carTypeId, int carMakeId, String carTypeName) {
    this.carTypeId = carTypeId;
    this.carMakeId = carMakeId;
    this.carTypeName = carTypeName;
  }

  public int getCarTypeId() {
    return carTypeId;
  }

  public int getCarMakeId() {
    return carMakeId;
  }

  public String getCarTypeName() {
    return carTypeName;
  }

  @Override
  public int hashCode() {
    return carTypeId * 31;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof SimpleCarTypeModel))
      return false;
    SimpleCarTypeModel model = (SimpleCarTypeModel) obj;
    return this.carTypeId == model.carTypeId;
  }
}
