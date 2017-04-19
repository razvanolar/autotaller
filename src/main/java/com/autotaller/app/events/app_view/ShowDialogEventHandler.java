package com.autotaller.app.events.app_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 19.04.2017
 */
public interface ShowDialogEventHandler extends EventHandler {
  void onShowDialogEvent(ShowDialogEvent event);
}
