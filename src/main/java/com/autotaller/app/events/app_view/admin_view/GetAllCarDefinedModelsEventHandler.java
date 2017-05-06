package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 05.05.2017
 */
public interface GetAllCarDefinedModelsEventHandler extends EventHandler {
  void onGetAlCarDefinedModelsEvent(GetAllCarDefinedModelsEvent event);
}
