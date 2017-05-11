package com.autotaller.app.utils.filters.car_model_filters;

import com.autotaller.app.model.CarModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 11.05.2017
 */
public class CarModelKWFilter implements CarModelFilter {

  private int from;
  private int to;

  public void setFrom(int from) {
    this.from = from;
  }

  public void setTo(int to) {
    this.to = to;
  }

  @Override
  public List<CarModel> filter(List<CarModel> cars) {
    if ((from <= 0 && to <= 0) || cars == null || cars.isEmpty())
      return cars;
    int lowerLimit = from;
    int upperLimit = to;
    if (from <= 0)
      lowerLimit = Integer.MIN_VALUE;
    if (to <= 0)
      upperLimit = Integer.MAX_VALUE;
    List<CarModel> result = new ArrayList<>();
    for (CarModel car : cars) {
      if (lowerLimit <= car.getKw() && car.getKw() <= upperLimit)
        result.add(car);
    }
    return result;
  }
}
