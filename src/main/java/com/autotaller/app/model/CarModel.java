package com.autotaller.app.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by razvanolar on 02.05.2017
 */
public class CarModel {

  private int id;
  private CarTypeModel carType;
  private String name;
  private LocalDate from;
  private LocalDate to;
  private int kw;
  private int capacity;
  private int cilinders;
  private List<String> enginesList;

  private String enginesString;

  public CarModel(int id, CarTypeModel carType, String name, LocalDate from, LocalDate to, int kw, int capacity, int cilinders, List<String> enginesList) {
    this.id = id;
    this.carType = carType;
    this.name = name;
    this.from = from;
    this.to = to;
    this.kw = kw;
    this.capacity = capacity;
    this.cilinders = cilinders;
    this.enginesList = enginesList;
  }

  public int getId() {
    return id;
  }

  public CarTypeModel getCarType() {
    return carType;
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

  public int getKw() {
    return kw;
  }

  public int getCapacity() {
    return capacity;
  }

  public int getCilinders() {
    return cilinders;
  }

  public List<String> getEnginesList() {
    return enginesList;
  }

  public String getEnginesString() {
    return enginesString;
  }

  private void computeEnginesString() {
    if (enginesList == null || enginesList.isEmpty()) {
      enginesString = "";
      return;
    }
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < enginesList.size(); i++) {
      builder.append(enginesList.get(i));
      if (i < enginesList.size() - 1) {
        builder.append(", ");
      }
    }
    enginesString = builder.toString();
  }
}
