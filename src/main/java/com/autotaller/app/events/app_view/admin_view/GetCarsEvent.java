package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.callbacks.LoadCarsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 08.05.2017
 */
public class GetCarsEvent extends Event<GetCarsEventHandler> {

  public static EventType<GetCarsEventHandler> TYPE = new EventType<>();

  private LoadCarsCallback callback;

  public GetCarsEvent(LoadCarsCallback callback) {
    this.callback = callback;
  }

  public LoadCarsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetCarsEventHandler handler) {
    handler.onGetCarsEvent(this);
  }
}
