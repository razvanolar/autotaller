package com.autotaller.app.events.login_view;

import com.autotaller.app.utils.callbacks.EmptyCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 04.06.2017
 */
public class CheckUserPasswordEvent extends Event<CheckUserPasswordEventHandler> {

  public static EventType<CheckUserPasswordEventHandler> TYPE = new EventType<>();

  private int userId;
  private String password;
  private EmptyCallback callback;

  public CheckUserPasswordEvent(int userId, String password, EmptyCallback callback) {
    this.userId = userId;
    this.password = password;
    this.callback = callback;
  }

  public int getUserId() {
    return userId;
  }

  public String getPassword() {
    return password;
  }

  public EmptyCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(CheckUserPasswordEventHandler handler) {
    handler.onCheckUserPasswordEvent(this);
  }
}
