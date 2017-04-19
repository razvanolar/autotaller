package com.autotaller.app.events.app_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 14.04.2017
 */
public interface ShowAppViewEventHandler extends EventHandler {
  void onShowAppViewEvent(ShowAppViewEvent event);
}
