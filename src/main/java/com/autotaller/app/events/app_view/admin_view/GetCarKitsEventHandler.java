package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 30.04.2017
 */
public interface GetCarKitsEventHandler extends EventHandler {
  void onGetCarKitsEvent(GetCarKitsEvent event);
}
