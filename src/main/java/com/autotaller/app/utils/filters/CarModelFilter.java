package com.autotaller.app.utils.filters;

import com.autotaller.app.model.CarModel;

import java.util.List;

/**
 * Created by razvanolar on 10.05.2017
 */
public interface CarModelFilter {
  List<CarModel> filter(List<CarModel> cars);
}
