package com.autotaller.app.events.app_view.admin_view.admin_car_model_view;

import com.autotaller.app.utils.event.EventHandler;

public interface DeleteCarModelEventHandler extends EventHandler {
  void onDeleteCarModelEvent(DeleteCarModelEvent event);
}
