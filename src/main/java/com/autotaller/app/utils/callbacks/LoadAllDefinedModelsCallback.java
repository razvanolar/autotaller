package com.autotaller.app.utils.callbacks;

import com.autotaller.app.model.utils.ModelsDTO;

/**
 * Created by razvanolar on 05.05.2017
 */
public interface LoadAllDefinedModelsCallback extends Callback {
  void call(ModelsDTO models);
}
