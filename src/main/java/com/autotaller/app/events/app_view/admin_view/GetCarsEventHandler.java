package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 08.05.2017
 */
public interface GetCarsEventHandler extends EventHandler {
  void onGetCarsEvent(GetCarsEvent event);
}
