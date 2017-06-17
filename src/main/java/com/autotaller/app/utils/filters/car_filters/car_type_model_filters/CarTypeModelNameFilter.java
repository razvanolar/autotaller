package com.autotaller.app.utils.filters.car_filters.car_type_model_filters;

import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.filters.car_filters.CarStringFilter;

/**
 * Created by razvanolar on 17.06.2017
 */
public class CarTypeModelNameFilter extends CarStringFilter<CarTypeModel> implements CarTypeModelFilter {

  public void setName(String name) {
    super.setFilterValue(name);
  }

  @Override
  protected String getValue(CarTypeModel model) {
    return model.getName();
  }
}
