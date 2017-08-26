package com.autotaller.app.events.app_view.admin_view.admin_car_make_view;

import com.autotaller.app.utils.event.EventHandler;

public interface DeleteCarMakeEventHandler extends EventHandler {
  void onDeleteCarMakeEvent(DeleteCarMakeEvent event);
}
