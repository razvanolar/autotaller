package com.autotaller.app.model;

import com.autotaller.app.utils.CarWheelSideType;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by razvanolar on 02.05.2017
 */
public class CarModel {

  private int id;
  private CarTypeModel carType;
  private String name;
  private CarBodyTypeModel bodyType;
  private LocalDate from;
  private LocalDate to;
  private LocalDate productionYear;
  private int km;
  private int kw;
  private int capacity;
  private int cilinders;
  private List<String> enginesList;
  private FuelModel fuel;
  private String colorCode;
  private int price;
  private CarWheelSideType wheelSide;
  private String description;

  private String enginesString;

  public CarModel(int id, CarTypeModel carType, String name, CarBodyTypeModel bodyType, LocalDate from, LocalDate to,
                  LocalDate productionYear, int km, int kw, int capacity, int cilinders, List<String> enginesList,
                  FuelModel fuel, String colorCode, int price, CarWheelSideType wheelSide, String description) {
    this.id = id;
    this.carType = carType;
    this.name = name;
    this.bodyType = bodyType;
    this.from = from;
    this.to = to;
    this.productionYear = productionYear;
    this.km = km;
    this.kw = kw;
    this.capacity = capacity;
    this.cilinders = cilinders;
    this.enginesList = enginesList;
    this.fuel = fuel;
    this.colorCode = colorCode;
    this.price = price;
    this.wheelSide = wheelSide;
    this.description = description;

    computeEnginesString();
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

  public CarBodyTypeModel getBodyType() {
    return bodyType;
  }

  public LocalDate getFrom() {
    return from;
  }

  public LocalDate getTo() {
    return to;
  }

  public LocalDate getProductionYear() {
    return productionYear;
  }

  public int getKm() {
    return km;
  }

  public int getKw() {
    return kw;
  }

  public float getHp() {
    return kw * (float) 1.34102;
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

  public FuelModel getFuel() {
    return fuel;
  }

  public String getColorCode() {
    return colorCode;
  }

  public int getPrice() {
    return price;
  }

  public CarWheelSideType getWheelSide() {
    return wheelSide;
  }

  public String getDescription() {
    return description;
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
