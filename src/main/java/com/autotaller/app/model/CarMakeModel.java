package com.autotaller.app.model;

/**
 * Created by razvanolar on 18.04.2017
 */
public class CarMakeModel {

  private int id;
  private String name;

  public CarMakeModel(int id, String name) {
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
  public int hashCode() {
    int hash = id;
    hash = hash * 31 + name.hashCode();
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof CarMakeModel))
      return false;
    CarMakeModel carMake = (CarMakeModel) obj;
    return this.id == carMake.id && this.name.equals(carMake.name);
  }

  @Override
  public String toString() {
    return name;
  }
}
