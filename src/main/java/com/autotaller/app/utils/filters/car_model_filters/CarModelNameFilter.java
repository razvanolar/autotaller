package com.autotaller.app.utils.filters.car_model_filters;

import com.autotaller.app.model.CarModel;
import com.autotaller.app.utils.StringValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 11.05.2017
 */
public class CarModelNameFilter implements CarModelFilter {

  private String name;

  public void setName(String name) {
    this.name = StringValidator.isNullOrEmpty(name) ? null : name.toLowerCase();
  }

  @Override
  public List<CarModel> filter(List<CarModel> cars) {
    if (StringValidator.isNullOrEmpty(name) || cars == null || cars.isEmpty())
      return cars;
    List<CarModel> result = new ArrayList<>();
    for (CarModel car : cars) {
      if (car.getName().toLowerCase().contains(name))
        result.add(car);
    }
    return result;
  }
}
