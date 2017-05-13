package com.autotaller.app.events.app_view.admin_view.admin_statistics_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 13.05.2017
 */
public interface GetCarTypeStatisticsHandler extends EventHandler {
  void onGetStatistincForCarTypesEvent(GetCarTypeStatistics event);
}
