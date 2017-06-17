package com.autotaller.app.utils.filters.car_filters.car_type_model_filters;

import com.autotaller.app.model.CarTypeModel;

import java.util.List;

/**
 * Created by razvanolar on 17.06.2017
 */
public interface CarTypeModelFilter {
  List<CarTypeModel> filter(List<CarTypeModel> carTypes);
}
