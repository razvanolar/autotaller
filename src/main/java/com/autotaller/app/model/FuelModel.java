package com.autotaller.app.model;

/**
 * Created by razvanolar on 04.05.2017
 */
public class FuelModel {

  private int id;
  private String name;

  private boolean isGasoline;
  private boolean isDiesel;
  private boolean isGPL;
  private boolean isElectric;

  public FuelModel(int id, String name) {
    this.id = id;
    this.name = name;
    this.isGasoline = id == 1;
    this.isDiesel = id == 2;
    this.isGPL = id == 3;
    this.isElectric = id == 4;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean isGasoline() {
    return isGasoline;
  }

  public boolean isDiesel() {
    return isDiesel;
  }

  public boolean isGPL() {
    return isGPL;
  }

  public boolean isElectric() {
    return isElectric;
  }

  @Override
  public int hashCode() {
    int hash = id;
    hash = hash * 31 + name.hashCode();
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof FuelModel))
      return false;
    FuelModel fuel = (FuelModel) obj;
    return this.id == fuel.id && this.name.equals(fuel.name);
  }

  @Override
  public String toString() {
    return name;
  }
}
