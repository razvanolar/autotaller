package com.autotaller.app.events.app_view.admin_view.admin_car_model_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 28.04.2017
 */
public interface AdminLoadCarModelsEventHandler extends EventHandler {
  void onAdminLoadCarModelsEvent(AdminLoadCarModelsEvent event);
}
