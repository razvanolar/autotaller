package com.autotaller.app.events.view_stack;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 12.04.2017
 */
public interface BackToPreviousViewEventHandler extends EventHandler {
  void onBackToPreviousViewEvent(BackToPreviousViewEvent event);
}
