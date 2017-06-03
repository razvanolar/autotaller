package com.autotaller.app.utils;

import com.autotaller.app.model.CarComponentModel;

/**
 * Created by razvanolar on 26.05.2017
 */
public class ModelValidator {

  public static boolean isValidCarComponent(CarComponentModel carComponent) {
    return carComponent != null && !StringValidator.isNullOrEmpty(carComponent.getName()) &&
            !StringValidator.isNullOrEmpty(carComponent.getCode()) &&
            carComponent.getCarSubkitId() > 0 &&
            carComponent.getInitialPieces() > 0 &&
            carComponent.getSoldPieces() > 0 &&
            carComponent.getInitialPieces() >= carComponent.getSoldPieces() &&
            carComponent.getPrice() > 0 &&
            carComponent.getUsageState() != null &&
            carComponent.getStock() != null;
  }
}
