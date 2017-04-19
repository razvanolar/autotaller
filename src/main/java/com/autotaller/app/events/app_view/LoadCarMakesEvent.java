package com.autotaller.app.events.app_view;

import com.autotaller.app.utils.callbacks.LoadingCarMakesCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 18.04.2017
 */
public class LoadCarMakesEvent extends Event<LoadCarMakesEventHandler> {

  public static EventType<LoadCarMakesEventHandler> TYPE = new EventType<>();

  private LoadingCarMakesCallback callback;

  public LoadCarMakesEvent(LoadingCarMakesCallback callback) {
    this.callback = callback;
  }

  public LoadingCarMakesCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(LoadCarMakesEventHandler handler) {
    handler.onLoadCarMakesEvent(this);
  }
}
