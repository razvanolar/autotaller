package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 27.05.2017
 */
public interface InjectCarComponentsEventHandler extends EventHandler {
  void onInjectPreviewCarComponentsEvent(InjectCarComponentsEvent event);
}
