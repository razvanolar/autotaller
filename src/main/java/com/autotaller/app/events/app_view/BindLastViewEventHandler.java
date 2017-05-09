package com.autotaller.app.events.app_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 09.05.2017
 */
public interface BindLastViewEventHandler extends EventHandler {
  void onBindLastViewEvent(BindLastViewEvent event);
}
