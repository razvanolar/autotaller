package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.utils.event.EventHandler;

public interface DeleteCarComponentEventHandler extends EventHandler {
  void onDeleteCarComponentEvent(DeleteCarComponentEvent event);
}
