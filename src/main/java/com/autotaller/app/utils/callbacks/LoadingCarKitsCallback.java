package com.autotaller.app.utils.callbacks;

import com.autotaller.app.model.CarKitModel;

import java.util.List;

/**
 * Created by razvanolar on 30.04.2017
 */
public interface LoadingCarKitsCallback extends Callback {
  void call(List<CarKitModel> carKits);
}
