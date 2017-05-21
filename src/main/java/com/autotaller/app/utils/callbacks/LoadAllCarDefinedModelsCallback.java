package com.autotaller.app.utils.callbacks;

import com.autotaller.app.model.utils.CarDefinedModelsDTO;

/**
 * Created by razvanolar on 14.05.2017
 */
public interface LoadAllCarDefinedModelsCallback extends Callback {
  void call(CarDefinedModelsDTO carModelsDTO);
}
