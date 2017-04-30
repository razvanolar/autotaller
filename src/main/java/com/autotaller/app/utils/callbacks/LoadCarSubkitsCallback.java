package com.autotaller.app.utils.callbacks;

import com.autotaller.app.model.CarSubkitModel;

import java.util.List;

/**
 * Created by razvanolar on 30.04.2017
 */
public interface LoadCarSubkitsCallback extends Callback {
  void call(List<CarSubkitModel> carSubkits);
}
