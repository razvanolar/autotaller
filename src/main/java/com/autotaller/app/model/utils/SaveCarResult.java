package com.autotaller.app.model.utils;

import com.autotaller.app.model.CarModel;
import com.autotaller.app.repository.utils.CarStatus;
import com.autotaller.app.repository.utils.ImageStatus;

/**
 * Created by razvanolar on 28.05.2017
 */
public class SaveCarResult {

  private CarModel carModel;
  private CarStatus carStatus;
  private ImageStatus imageStatus;

  public SaveCarResult(CarModel carModel, CarStatus carStatus, ImageStatus imageStatus) {
    this.carModel = carModel;
    this.carStatus = carStatus;
    this.imageStatus = imageStatus;
  }

  public CarModel getCarModel() {
    return carModel;
  }

  public CarStatus getCarStatus() {
    return carStatus;
  }

  public ImageStatus getImageStatus() {
    return imageStatus;
  }
}
