package com.autotaller.app.utils.callbacks;

import com.autotaller.app.model.CarModel;

import java.util.List;

/**
 * Created by razvanolar on 08.05.2017
 */
public interface LoadCarsCallback extends Callback {
  void call(List<CarModel> cars);
}
