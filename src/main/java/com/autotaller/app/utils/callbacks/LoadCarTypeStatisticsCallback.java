package com.autotaller.app.utils.callbacks;

import com.autotaller.app.components.utils.statistics.CarTypeStatisticsModel;

/**
 * Created by razvanolar on 13.05.2017
 */
public interface LoadCarTypeStatisticsCallback extends Callback {
  void call(CarTypeStatisticsModel carTypeStatistics);
}
