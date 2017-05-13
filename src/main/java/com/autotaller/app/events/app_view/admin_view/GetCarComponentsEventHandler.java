package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 13.05.2017
 */
public interface GetCarComponentsEventHandler extends EventHandler {
  void onGetCarComponentsEvent(GetCarComponentsEvent event);
}
