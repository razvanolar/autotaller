package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 18.04.2017
 */
public interface GetCarMakesEventHandler extends EventHandler {
  void onLoadCarMakesEvent(GetCarMakesEvent event);
}
