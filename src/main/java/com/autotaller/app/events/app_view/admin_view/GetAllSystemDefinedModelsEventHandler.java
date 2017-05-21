package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 05.05.2017
 */
public interface GetAllSystemDefinedModelsEventHandler extends EventHandler {
  void onGetAlCarDefinedModelsEvent(GetAllSystemDefinedModelsEvent event);
}
