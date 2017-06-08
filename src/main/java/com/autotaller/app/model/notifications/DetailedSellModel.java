package com.autotaller.app.model.notifications;

import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.model.CarSubkitModel;

import java.time.LocalDate;

/**
 * Created by razvanolar on 08.06.2017
 */
public class DetailedSellModel {

  private int id;
  private CarComponentModel carComponent;
  private CarModel car;
  private CarSubkitModel carSubkitModel;
  private int soldPieces;
  private int stockPrice;
  private int soldPrice;
  private LocalDate soldDate;
  private int userId;
  private String userName;
  private int status;

  public DetailedSellModel(int id, CarComponentModel carComponent, CarModel car, CarSubkitModel carSubkitModel,
                           int soldPieces, int stockPrice, int soldPrice, LocalDate soldDate, int userId, String userName, int status) {
    this.id = id;
    this.carComponent = carComponent;
    this.car = car;
    this.carSubkitModel = carSubkitModel;
    this.soldPieces = soldPieces;
    this.stockPrice = stockPrice;
    this.soldPrice = soldPrice;
    this.soldDate = soldDate;
    this.userId = userId;
    this.userName = userName;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public CarComponentModel getCarComponent() {
    return carComponent;
  }

  public CarModel getCar() {
    return car;
  }

  public CarSubkitModel getCarSubkitModel() {
    return carSubkitModel;
  }

  public int getSoldPieces() {
    return soldPieces;
  }

  public int getStockPrice() {
    return stockPrice;
  }

  public int getSoldPrice() {
    return soldPrice;
  }

  public LocalDate getSoldDate() {
    return soldDate;
  }

  public int getUserId() {
    return userId;
  }

  public String getUserName() {
    return userName;
  }

  public int getStatus() {
    return status;
  }
}
