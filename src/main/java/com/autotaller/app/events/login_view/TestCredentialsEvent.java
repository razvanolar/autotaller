package com.autotaller.app.events.login_view;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 14.04.2017
 */
public class TestCredentialsEvent extends Event<TestCredentialsEventHandler> {

  public static EventType<TestCredentialsEventHandler> TYPE = new EventType<>();

  private String username;
  private String password;

  public TestCredentialsEvent(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(TestCredentialsEventHandler handler) {
    handler.onTestCredentialsEvent(this);
  }
}
