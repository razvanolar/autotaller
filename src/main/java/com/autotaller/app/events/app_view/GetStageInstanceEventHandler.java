package com.autotaller.app.events.app_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 27.05.2017
 */
public interface GetStageInstanceEventHandler extends EventHandler {
  void onGetStageInstanceEvent(GetStageInstanceEvent event);
}
