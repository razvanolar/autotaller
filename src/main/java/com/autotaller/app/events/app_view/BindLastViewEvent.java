package com.autotaller.app.events.app_view;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Fired after a new component is added to the stack view so the controller can finish its binding operations
 *
 * Created by razvanolar on 09.05.2017
 */
public class BindLastViewEvent extends Event<BindLastViewEventHandler> {

  public static EventType<BindLastViewEventHandler> TYPE = new EventType<>();

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(BindLastViewEventHandler handler) {
    handler.onBindLastViewEvent(this);
  }
}
