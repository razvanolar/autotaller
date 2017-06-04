package com.autotaller.app.model.utils;

import com.autotaller.app.model.CarComponentModel;

/**
 * Created by razvanolar on 03.06.2017
 */
public class PreSellComponentModel {

  private CarComponentModel carComponent;
  private int sold_pieces;
  private int price;

  public PreSellComponentModel(CarComponentModel carComponent, int sold_pieces, int price) {
    this.carComponent = carComponent;
    this.sold_pieces = sold_pieces;
    this.price = price;
  }

  public CarComponentModel getCarComponent() {
    return carComponent;
  }

  public int getSoldPieces() {
    return sold_pieces;
  }

  public int getPrice() {
    return price;
  }

  public void setCarComponent(CarComponentModel carComponent) {
    this.carComponent = carComponent;
  }

  public void setSoldPieces(int sold_pieces) {
    this.sold_pieces = sold_pieces;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
