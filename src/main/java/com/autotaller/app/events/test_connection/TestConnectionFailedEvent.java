package com.autotaller.app.events.test_connection;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 11.04.2017
 */
public class TestConnectionFailedEvent extends Event<TestConnectionFailedEventHandler> {

  public static EventType<TestConnectionFailedEventHandler> TYPE = new EventType<>();

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(TestConnectionFailedEventHandler handler) {
    handler.onTestConnectionFailedEvent(this);
  }
}
