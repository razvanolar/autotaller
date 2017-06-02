package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 02.06.2017
 */
public interface InjectCarKitInformationEventHandler extends EventHandler {
  void onInjectCarKitEvent(InjectCarKitInformationEvent event);
}
