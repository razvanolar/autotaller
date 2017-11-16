package com.autotaller.app.events.app_view.admin_view.admin_car_view;

import com.autotaller.app.utils.event.EventHandler;

public interface SearchCarEventHandler extends EventHandler {
  void onSearchCarEvent(SearchCarEvent event);
}
