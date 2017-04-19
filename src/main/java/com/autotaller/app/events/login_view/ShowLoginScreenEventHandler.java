package com.autotaller.app.events.login_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 11.04.2017
 */
public interface ShowLoginScreenEventHandler extends EventHandler {
  void onShowLoadingScreenEvent(ShowLoginScreenEvent event);
}
