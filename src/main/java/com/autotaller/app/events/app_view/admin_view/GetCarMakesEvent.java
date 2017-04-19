package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.callbacks.LoadingCarMakesCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 18.04.2017
 */
public class GetCarMakesEvent extends Event<GetCarMakesEventHandler> {

  public static EventType<GetCarMakesEventHandler> TYPE = new EventType<>();

  private LoadingCarMakesCallback callback;

  public GetCarMakesEvent(LoadingCarMakesCallback callback) {
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
  public void dispatch(GetCarMakesEventHandler handler) {
    handler.onLoadCarMakesEvent(this);
  }
}
