package com.autotaller.app.utils.filters.car_filters.car_model_filters;

import com.autotaller.app.model.CarModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 11.05.2017
 */
public class CarModelCilindersFilter implements CarModelFilter {

  private int cilinders;

  public void setCilinders(int cilinders) {
    this.cilinders = cilinders;
  }

  @Override
  public List<CarModel> filter(List<CarModel> cars) {
    if (cilinders <= 0 || cars == null || cars.isEmpty())
      return cars;
    List<CarModel> result = new ArrayList<>();
    for (CarModel car : cars) {
      if (car.getCilinders() == cilinders)
        result.add(car);
    }
    return result;
  }
}
