package com.autotaller.app.events.app_view.admin_view.admin_car_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 07.05.2017
 */
public interface AddCarEventHandler extends EventHandler {
  void onAddCarEvent(AddCarEvent event);
}
