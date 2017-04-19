package com.autotaller.app.events.test_connection;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 11.04.2017
 */
public class TestConnectionEvent extends Event<TestConnectionEventHandler> {

  public static EventType<TestConnectionEventHandler> TYPE = new EventType<>();

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(TestConnectionEventHandler handler) {
    handler.onTestConnectionEvent(this);
  }
}
