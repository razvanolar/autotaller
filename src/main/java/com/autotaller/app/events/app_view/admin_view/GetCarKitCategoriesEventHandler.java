package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 30.04.2017
 */
public interface GetCarKitCategoriesEventHandler extends EventHandler {
  void onGetCarKitCategoriesEvent(GetCarKitCategoriesEvent event);
}
