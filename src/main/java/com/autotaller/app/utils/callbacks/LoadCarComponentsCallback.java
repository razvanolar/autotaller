package com.autotaller.app.utils.callbacks;

import com.autotaller.app.model.CarComponentModel;

import java.util.List;

/**
 * Created by razvanolar on 13.05.2017
 */
public interface LoadCarComponentsCallback extends Callback {
  void call(List<CarComponentModel> carComponents);
}
