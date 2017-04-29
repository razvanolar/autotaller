package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.callbacks.LoadingCarKitsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 30.04.2017
 */
public class GetCarKitsEvent extends Event<GetCarKitsEventHandler> {

  public static EventType<GetCarKitsEventHandler> TYPE = new EventType<>();

  private LoadingCarKitsCallback callback;

  public GetCarKitsEvent(LoadingCarKitsCallback callback) {
    this.callback = callback;
  }

  public LoadingCarKitsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetCarKitsEventHandler handler) {
    handler.onGetCarKitsEvent(this);
  }
}
