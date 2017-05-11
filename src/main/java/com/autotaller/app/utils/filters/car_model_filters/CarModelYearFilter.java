package com.autotaller.app.utils.filters.car_model_filters;

import com.autotaller.app.model.CarModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 10.05.2017
 */
public class CarModelYearFilter implements CarModelFilter {

  private ObservableList<Integer> years;

  public CarModelYearFilter() {
    years = FXCollections.observableList(new ArrayList<>());
  }

  public ObservableList<Integer> getYears() {
    return years;
  }

  @Override
  public List<CarModel> filter(List<CarModel> cars) {
    if (years.isEmpty())
      return cars;
    if (cars == null)
      return new ArrayList<>();
    List<CarModel> result = new ArrayList<>();
    for (CarModel car : cars) {
      int toYear = car.getTo() != null ? car.getTo().getYear() : -1;
      for (int year : years) {
        // 'to' year is available
        if (toYear > 0) {
          if (car.getFrom().getYear() <= year && year <= toYear) {
            result.add(car);
            break;
          }
        } else if (car.getFrom().getYear() <= year) {
          result.add(car);
          break;
        }
      }
    }
    return result;
  }
}
