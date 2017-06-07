package com.autotaller.app.model.notifications;

import java.time.LocalDate;

/**
 * Created by razvanolar on 04.06.2017
 */
public class SimpleSellModel {

  private int id;
  private int componentId;
  private String componentName;
  private int soldPieces;
  private int stockPrice;
  private int soldPrice;
  private LocalDate soldDate;
  private int userId;
  private String userName;
  private int status;

  public SimpleSellModel(int id, int componentId, String componentName, int soldPieces, int stockPrice, int soldPrice, LocalDate soldDate, int userId, String userName, int status) {
    this.id = id;
    this.componentId = componentId;
    this.componentName = componentName;
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

  public int getComponentId() {
    return componentId;
  }

  public String getComponentName() {
    return componentName;
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

  public void setComponentId(int componentId) {
    this.componentId = componentId;
  }

  public void setComponentName(String componentName) {
    this.componentName = componentName;
  }

  public void setSoldPieces(int soldPieces) {
    this.soldPieces = soldPieces;
  }

  public void setStockPrice(int stockPrice) {
    this.stockPrice = stockPrice;
  }

  public void setSoldPrice(int soldPrice) {
    this.soldPrice = soldPrice;
  }

  public void setSoldDate(LocalDate soldDate) {
    this.soldDate = soldDate;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
