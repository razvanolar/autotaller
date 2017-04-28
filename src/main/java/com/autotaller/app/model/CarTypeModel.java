package com.autotaller.app.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by razvanolar on 20.04.2017
 */
public class CarTypeModel {

  private int id;
  private CarMakeModel carMake;
  private String name;
  private LocalDate from;
  private LocalDate to;
  private List<String> engineNames;

  public CarTypeModel(int id, CarMakeModel carMake, String name, LocalDate from, LocalDate to, List<String> engineNames) {
    this.id = id;
    this.carMake = carMake;
    this.name = name;
    this.from = from;
    this.to = to;
    this.engineNames = engineNames;
  }

  public CarMakeModel getCarMake() {
    return carMake;
  }

  public String getName() {
    return name;
  }

  public LocalDate getFrom() {
    return from;
  }

  public LocalDate getTo() {
    return to;
  }

  public List<String> getEngineNames() {
    return engineNames;
  }

  public void setCarMake(CarMakeModel carMake) {
    this.carMake = carMake;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setFrom(LocalDate from) {
    this.from = from;
  }

  public void setTo(LocalDate to) {
    this.to = to;
  }
}
