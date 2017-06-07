package com.autotaller.app.utils.callbacks;

import com.autotaller.app.model.notifications.SimpleSellModel;

import java.util.List;

/**
 * Created by razvanolar on 07.06.2017
 */
public interface LoadSimpleSellModelsCallback extends Callback {
  void call(List<SimpleSellModel> sellModels);
}
