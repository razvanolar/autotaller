package com.autotaller.app.utils.callbacks;

import com.autotaller.app.model.CarMakeModel;

import java.util.List;

/**
 * Created by razvanolar on 18.04.2017
 */
public interface LoadingCarMakesCallback extends Callback {
  void call(List<CarMakeModel> cars);
}
