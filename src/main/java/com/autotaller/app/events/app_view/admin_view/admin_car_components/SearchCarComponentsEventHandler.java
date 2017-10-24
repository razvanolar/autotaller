package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.utils.event.EventHandler;

public interface SearchCarComponentsEventHandler extends EventHandler {
  void onSearchCarComponentsEvent(SearchCarComponentsEvent event);
}
