package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 14.05.2017
 */
public interface GetAllCarDefinedModelsEventHandler extends EventHandler {
  void onGetAllCarDefinedModelsEvent(GetAllCarDefinedModelsEvent event);
}
