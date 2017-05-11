package com.autotaller.app.utils.filters.car_model_filters;

import com.autotaller.app.model.CarModel;
import com.autotaller.app.model.CarTypeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 11.05.2017
 */
public class CarModelTypeFilter implements CarModelFilter {

  private CarTypeModel carType;

  public void setCarType(CarTypeModel carType) {
    this.carType = carType;
  }

  @Override
  public List<CarModel> filter(List<CarModel> cars) {
    if (carType == null || carType.getId() == -1 || cars == null || cars.isEmpty())
      return cars;
    List<CarModel> result = new ArrayList<>();
    for (CarModel car : cars) {
      if (car.getCarType().getId() == carType.getId())
        result.add(car);
    }
    return result;
  }
}
