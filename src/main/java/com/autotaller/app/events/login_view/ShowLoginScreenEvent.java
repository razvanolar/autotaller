package com.autotaller.app.events.login_view;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 11.04.2017
 */
public class ShowLoginScreenEvent extends Event<ShowLoginScreenEventHandler> {

  public static EventType<ShowLoginScreenEventHandler> TYPE = new EventType<>();

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(ShowLoginScreenEventHandler handler) {
    handler.onShowLoadingScreenEvent(this);
  }
}
