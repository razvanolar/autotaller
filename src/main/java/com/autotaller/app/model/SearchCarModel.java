package com.autotaller.app.model;

import com.autotaller.app.utils.CarWheelSideType;

import java.util.List;

public class SearchCarModel {

  private CarMakeModel carMake;
  private CarTypeModel carType;
  private int parkNumber;
  private int productionYear;
  private List<CarWheelSideType> wheelSideList;
  private int powerFrom;
  private int powerTo;
  private boolean useKWUnit;
  private int kmFrom;
  private int kmTo;
  private int capacityFrom;
  private int capacityTo;
  private int priceFrom;
  private int priceTo;
  private List<FuelModel> fuelList;
  private List<CarBodyTypeModel> bodyTypeList;

  public SearchCarModel(CarMakeModel carMake, CarTypeModel carType, int parkNumber, int productionYear,
                        List<CarWheelSideType> wheelSideList, int powerFrom, int powerTo, boolean useKWUnit, int kmFrom,
                        int kmTo, int capacityFrom, int capacityTo, int priceFrom, int priceTo, List<FuelModel> fuelList,
                        List<CarBodyTypeModel> bodyTypeList) {
    this.carMake = carMake;
    this.carType = carType;
    this.parkNumber = parkNumber;
    this.productionYear = productionYear;
    this.wheelSideList = wheelSideList;
    this.powerFrom = powerFrom;
    this.powerTo = powerTo;
    this.useKWUnit = useKWUnit;
    this.kmFrom = kmFrom;
    this.kmTo = kmTo;
    this.capacityFrom = capacityFrom;
    this.capacityTo = capacityTo;
    this.priceFrom = priceFrom;
    this.priceTo = priceTo;
    this.fuelList = fuelList;
    this.bodyTypeList = bodyTypeList;
  }

  public CarMakeModel getCarMake() {
    return carMake;
  }

  public CarTypeModel getCarType() {
    return carType;
  }

  public int getParkNumber() {
    return parkNumber;
  }

  public int getProductionYear() {
    return productionYear;
  }

  public List<CarWheelSideType> getWheelSideList() {
    return wheelSideList;
  }

  public int getPowerFrom() {
    return powerFrom;
  }

  public int getPowerTo() {
    return powerTo;
  }

  public boolean isUseKWUnit() {
    return useKWUnit;
  }

  public int getKmFrom() {
    return kmFrom;
  }

  public int getKmTo() {
    return kmTo;
  }

  public int getCapacityFrom() {
    return capacityFrom;
  }

  public int getCapacityTo() {
    return capacityTo;
  }

  public int getPriceFrom() {
    return priceFrom;
  }

  public int getPriceTo() {
    return priceTo;
  }

  public List<FuelModel> getFuelList() {
    return fuelList;
  }

  public List<CarBodyTypeModel> getBodyTypeList() {
    return bodyTypeList;
  }
}
