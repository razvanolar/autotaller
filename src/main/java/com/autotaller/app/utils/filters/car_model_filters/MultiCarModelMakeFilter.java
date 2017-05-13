package com.autotaller.app.utils.filters.car_model_filters;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.utils.filters.Filter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 13.05.2017
 */
public class MultiCarModelMakeFilter implements CarModelFilter, Filter<CarMakeModel> {

  private ObservableList<CarMakeModel> carMakes;

  public MultiCarModelMakeFilter() {
    this.carMakes = FXCollections.observableList(new ArrayList<>());
  }

  @Override
  public List<CarMakeModel> getFields() {
    return carMakes;
  }

  @Override
  public List<CarModel> filter(List<CarModel> cars) {
    return null;
  }
}
