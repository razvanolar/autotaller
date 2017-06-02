package com.autotaller.app.model;

/**
 * Created by razvanolar on 07.05.2017
 */
public class CarComponentModel {

  private int id;
  private int carId;
  private int carSubkitId;
  private String name;
  private String code;
  private String stock;
  private String description;
  private int initial_pieces;
  private int sold_pieces;

  public CarComponentModel(int id, int carId, int carSubkitId, String name, String code, String stock,
                           String description, int initial_pieces, int sold_pieces) {
    this.id = id;
    this.carId = carId;
    this.carSubkitId = carSubkitId;
    this.name = name;
    this.code = code;
    this.stock = stock;
    this.description = description;
    this.initial_pieces = initial_pieces;
    this.sold_pieces = sold_pieces;
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

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public String getStock() {
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

  public void setName(String name) {
    this.name = name;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setStock(String stock) {
    this.stock = stock;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setInitial_pieces(int initial_pieces) {
    this.initial_pieces = initial_pieces;
  }
}
