package com.autotaller.app.events.app_view.admin_view.admin_car_kit_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 30.04.2017
 */
public interface AdminLoadCarKitCategoreisEventHandler extends EventHandler {
  void onAdminLoadCarKitsEvent(AdminLoadCarKitCategoriesEvent event);
}
