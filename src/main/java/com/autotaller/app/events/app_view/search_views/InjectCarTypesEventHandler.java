package com.autotaller.app.events.app_view.search_views;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 02.06.2017
 */
public interface InjectCarTypesEventHandler extends EventHandler {
  void onInjectSystemDefinedModelsEvent(InjectCarTypesEvent event);
}
