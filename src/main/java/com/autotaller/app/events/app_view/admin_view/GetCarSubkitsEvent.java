package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.callbacks.LoadCarSubkitsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 30.04.2017
 */
public class GetCarSubkitsEvent extends Event<GetCarSubkitsEventHandler> {

  public static EventType<GetCarSubkitsEventHandler> TYPE = new EventType<>();

  private LoadCarSubkitsCallback callback;

  public GetCarSubkitsEvent(LoadCarSubkitsCallback callback) {
    this.callback = callback;
  }

  public LoadCarSubkitsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetCarSubkitsEventHandler handler) {
    handler.onGetCarSubkitsEvent(this);
  }
}
