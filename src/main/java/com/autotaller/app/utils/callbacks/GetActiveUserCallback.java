package com.autotaller.app.utils.callbacks;

import com.autotaller.app.model.UserModel;

/**
 * Created by razvanolar on 03.06.2017
 */
public interface GetActiveUserCallback extends Callback {
  void call(UserModel user);
}
