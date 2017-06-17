package com.autotaller.app.utils.filters.car_model_filters;

import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.filters.Filter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 17.06.2017
 */
public class CarTypeModelYearsFilter implements CarTypeModelFilter, Filter<Integer> {

  private ObservableList<Integer> years;

  public CarTypeModelYearsFilter() {
    years = FXCollections.observableList(new ArrayList<>());
  }

  @Override
  public ObservableList<Integer> getFields() {
    return years;
  }

  @Override
  public List<CarTypeModel> filter(List<CarTypeModel> carTypes) {
    if (years.isEmpty())
      return carTypes;
    if (carTypes == null)
      return new ArrayList<>();
    List<CarTypeModel> result = new ArrayList<>();
    for (CarTypeModel carType : carTypes) {
      int toYear = carType.getTo() != null ? carType.getTo().getYear() : -1;
      for (int year : years) {
        // 'to' year is available
        if (toYear > 0) {
          if (carType.getFrom().getYear() <= year && year <= toYear) {
            result.add(carType);
            break;
          }
        } else if (carType.getFrom().getYear() <= year) {
          result.add(carType);
          break;
        }
      }
    }
    return result;
  }
}
