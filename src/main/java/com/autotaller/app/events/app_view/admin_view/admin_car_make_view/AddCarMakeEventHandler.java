package com.autotaller.app.events.app_view.admin_view.admin_car_make_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 20.04.2017
 */
public interface AddCarMakeEventHandler extends EventHandler {
  void onAddCarMakeEvent(AddCarMakeEvent event);
}
