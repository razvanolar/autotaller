package com.autotaller.app.events.test_connection;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 11.04.2017
 */
public interface TestConnectionFailedEventHandler extends EventHandler {
  void onTestConnectionFailedEvent(TestConnectionFailedEvent event);
}
