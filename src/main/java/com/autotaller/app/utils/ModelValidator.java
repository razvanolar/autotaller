package com.autotaller.app.utils;

import com.autotaller.app.model.CarComponentModel;

/**
 * Created by razvanolar on 26.05.2017
 */
public class ModelValidator {

  public static boolean isValidCarComponent(CarComponentModel carComponent) {
    return carComponent != null && !StringValidator.isNullOrEmpty(carComponent.getName()) &&
            !StringValidator.isNullOrEmpty(carComponent.getCode()) &&
            !StringValidator.isNullOrEmpty(carComponent.getStock()) &&
            carComponent.getCarSubkitId() > 0;
  }
}
