package com.autotaller.app.events.view_stack;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 12.04.2017
 */
public class BackToPreviousViewEvent extends Event<BackToPreviousViewEventHandler> {

  public static EventType<BackToPreviousViewEventHandler> TYPE = new EventType<>();

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(BackToPreviousViewEventHandler handler) {
    handler.onBackToPreviousViewEvent(this);
  }
}
