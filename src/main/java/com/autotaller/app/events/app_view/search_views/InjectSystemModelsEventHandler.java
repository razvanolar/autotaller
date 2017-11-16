package com.autotaller.app.events.app_view.search_views;

import com.autotaller.app.utils.event.EventHandler;

public interface InjectSystemModelsEventHandler extends EventHandler {
  void onInjectSystemModelsEvent(InjectSystemModelsEvent event);
}
