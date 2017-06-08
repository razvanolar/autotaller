package com.autotaller.app.utils.callbacks;

import com.autotaller.app.model.notifications.DetailedSellModel;

/**
 * Created by razvanolar on 08.06.2017
 */
public interface LoadDetailedSellModelCallback extends Callback {
  void call(DetailedSellModel sellModel);
}
