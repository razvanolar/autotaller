package com.autotaller.app.events.login_view;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 14.04.2017
 */
public class ReleaseLoginViewEvent extends Event<ReleaseLoginViewEventHandler> {

  public static EventType<ReleaseLoginViewEventHandler> TYPE = new EventType<>();

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(ReleaseLoginViewEventHandler handler) {
    handler.onReleaseLoginViewEvent(this);
  }
}
