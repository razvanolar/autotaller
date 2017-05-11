package com.autotaller.app.utils.filters.car_model_filters;

import com.autotaller.app.model.CarModel;
import com.autotaller.app.model.FuelModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 11.05.2017
 */
public class CarModelFuelFilter implements CarModelFilter {

  private FuelModel fuel;

  public void setFuel(FuelModel fuel) {
    this.fuel = fuel;
  }

  @Override
  public List<CarModel> filter(List<CarModel> cars) {
    if (fuel == null || fuel.getId() == -1 || cars == null || cars.isEmpty())
      return cars;
    List<CarModel> result = new ArrayList<>();
    for (CarModel car : cars) {
      if (car.getFuel().equals(fuel))
        result.add(car);
    }
    return result;
  }
}
