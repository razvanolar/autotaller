package com.autotaller.app.events.login_view;

import com.autotaller.app.utils.callbacks.GetActiveUserCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 03.06.2017
 */
public class GetActiveUserEvent extends Event<GetActiveUserEventHandler> {

  public static EventType<GetActiveUserEventHandler> TYPE = new EventType<>();

  private GetActiveUserCallback callback;

  public GetActiveUserEvent(GetActiveUserCallback callback) {
    this.callback = callback;
  }

  public GetActiveUserCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetActiveUserEventHandler handler) {
    handler.onGetActiveUserEvent(this);
  }
}
