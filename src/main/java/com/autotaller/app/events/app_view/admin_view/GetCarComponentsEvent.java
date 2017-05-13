package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.callbacks.LoadCarComponentsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 13.05.2017
 */
public class GetCarComponentsEvent extends Event<GetCarComponentsEventHandler> {

  public static EventType<GetCarComponentsEventHandler> TYPE = new EventType<>();

  private LoadCarComponentsCallback callback;

  public GetCarComponentsEvent(LoadCarComponentsCallback callback) {
    this.callback = callback;
  }

  public LoadCarComponentsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetCarComponentsEventHandler handler) {
    handler.onGetCarComponentsEvent(this);
  }
}
