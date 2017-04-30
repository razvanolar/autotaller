package com.autotaller.app.model;

/**
 * Created by razvanolar on 30.04.2017
 */
public class CarSubkitModel {

  private int id;
  private String name;
  private CarKitModel carKit;
  private boolean isUsedForGasoline;
  private boolean isUsedForDiesel;
  private boolean isUsedForGPL;
  private boolean isUsedForElectric;

  public CarSubkitModel(int id, String name, CarKitModel carKit, boolean isUsedForGasoline, boolean isUsedForDiesel, boolean isUsedForGPL, boolean isUsedForElectric) {
    this.id = id;
    this.name = name;
    this.carKit = carKit;
    this.isUsedForGasoline = isUsedForGasoline;
    this.isUsedForDiesel = isUsedForDiesel;
    this.isUsedForGPL = isUsedForGPL;
    this.isUsedForElectric = isUsedForElectric;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public CarKitModel getCarKit() {
    return carKit;
  }

  public boolean isUsedForGasoline() {
    return isUsedForGasoline;
  }

  public boolean isUsedForDiesel() {
    return isUsedForDiesel;
  }

  public boolean isUsedForGPL() {
    return isUsedForGPL;
  }

  public boolean isUsedForElectric() {
    return isUsedForElectric;
  }
}
