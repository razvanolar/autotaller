package com.autotaller.app.events.login_view;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 14.04.2017
 */
public class AuthenticationFailedEvent extends Event<AuthenticationFailedEventHandler> {

  public static EventType<AuthenticationFailedEventHandler> TYPE = new EventType<>();

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AuthenticationFailedEventHandler handler) {
    handler.onAuthenticationFailedEvent(this);
  }
}
