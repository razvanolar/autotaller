package com.autotaller.app.events.app_view.admin_view.admin_car_make_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 19.04.2017
 */
public interface AdminLoadCarMakesEventHandler extends EventHandler {
  void onAdminLoadCarMakesEvent(AdminLoadCarMakesEvent event);
}
