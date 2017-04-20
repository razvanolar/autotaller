package com.autotaller.app.model;

import java.util.Date;

/**
 * Created by razvanolar on 20.04.2017
 */
public class CarTypeModel {

  private CarMakeModel carMake;
  private String name;
  private Date from;
  private Date to;

  public CarTypeModel(CarMakeModel carMake, String name, Date from, Date to) {
    this.carMake = carMake;
    this.name = name;
    this.from = from;
    this.to = to;
  }

  public CarMakeModel getCarMake() {
    return carMake;
  }

  public String getName() {
    return name;
  }

  public Date getFrom() {
    return from;
  }

  public Date getTo() {
    return to;
  }

  public void setCarMake(CarMakeModel carMake) {
    this.carMake = carMake;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setFrom(Date from) {
    this.from = from;
  }

  public void setTo(Date to) {
    this.to = to;
  }
}
