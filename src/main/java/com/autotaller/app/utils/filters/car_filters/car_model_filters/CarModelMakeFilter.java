package com.autotaller.app.utils.filters.car_filters.car_model_filters;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Used when we have to filter only by one car make. (i.e. in a combo box)
 * Created by razvanolar on 11.05.2017
 */
public class CarModelMakeFilter implements CarModelFilter {

  private CarMakeModel carMake;

  public void setCarMake(CarMakeModel carMake) {
    this.carMake = carMake;
  }

  @Override
  public List<CarModel> filter(List<CarModel> cars) {
    if (carMake == null || carMake.getId() == -1 || cars == null || cars.isEmpty())
      return cars;
    List<CarModel> result = new ArrayList<>();
    for (CarModel car : cars) {
      if (car.getCarType().getCarMake().equals(carMake))
        result.add(car);
    }
    return result;
  }
}
