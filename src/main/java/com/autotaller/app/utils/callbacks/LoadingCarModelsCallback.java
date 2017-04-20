package com.autotaller.app.utils.callbacks;

import com.autotaller.app.model.CarTypeModel;

import java.util.List;

/**
 * Created by razvanolar on 20.04.2017
 */
public interface LoadingCarModelsCallback extends Callback {
  void call(List<CarTypeModel> carModels);
}
