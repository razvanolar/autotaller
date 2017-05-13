package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 13.05.2017
 */
public interface InjectCarInformationEventHandler extends EventHandler {
  void onInjectCarInformationEvent(InjectCarInformationEvent event);
}
