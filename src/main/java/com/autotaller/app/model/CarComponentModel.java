package com.autotaller.app.model;

import com.autotaller.app.utils.StockType;
import com.autotaller.app.utils.UsageStateType;

/**
 * Created by razvanolar on 07.05.2017
 */
public class CarComponentModel {

  private int id;
  private int carId;
  private int carSubkitId;
  private String carSubkitName;
  private String name;
  private String code;
  private String description;
  private int initial_pieces;
  private int sold_pieces;
  private UsageStateType usageState;
  private int price;
  private StockType stock;

  public CarComponentModel(int id, int carId, int carSubkitId, String carSubkitName, String name, String code, String description,
                           int initial_pieces, int sold_pieces, UsageStateType usageState, int price, StockType stock) {
    this.id = id;
    this.carId = carId;
    this.carSubkitId = carSubkitId;
    this.carSubkitName = carSubkitName;
    this.name = name;
    this.code = code;
    this.description = description;
    this.initial_pieces = initial_pieces;
    this.sold_pieces = sold_pieces;
    this.usageState = usageState;
    this.price = price;
    this.stock = stock;
  }

  public int getLeftPieces() {
    if (initial_pieces >= sold_pieces)
      return initial_pieces - sold_pieces;
    return 0;
  }

  public int getId() {
    return id;
  }

  public int getCarId() {
    return carId;
  }

  public int getCarSubkitId() {
    return carSubkitId;
  }

  public String getCarSubkitName() {
    return carSubkitName;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public StockType getStock() {
    return stock;
  }

  public String getDescription() {
    return description;
  }

  public int getInitialPieces() {
    return initial_pieces;
  }

  public int getSoldPieces() {
    return sold_pieces;
  }

  public int getPrice() {
    return price;
  }

  public UsageStateType getUsageState() {
    return usageState;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setStock(StockType stock) {
    this.stock = stock;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setInitialPieces(int initial_pieces) {
    this.initial_pieces = initial_pieces;
  }

  public void setCarId(int carId) {
    this.carId = carId;
  }

  public void setCarSubkitId(int carSubkitId) {
    this.carSubkitId = carSubkitId;
  }

  public void setSoldPieces(int sold_pieces) {
    this.sold_pieces = sold_pieces;
  }

  public void setUsageState(UsageStateType usageState) {
    this.usageState = usageState;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
