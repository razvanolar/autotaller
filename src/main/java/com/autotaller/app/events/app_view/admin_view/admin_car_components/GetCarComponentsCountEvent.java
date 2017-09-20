package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.utils.callbacks.GetIntegerCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

public class GetCarComponentsCountEvent extends Event<GetCarComponentsCountEventHandler> {

  public static EventType<GetCarComponentsCountEventHandler> TYPE = new EventType<>();

  private GetIntegerCallback callback;

  public GetCarComponentsCountEvent(GetIntegerCallback callback) {
    this.callback = callback;
  }

  public GetIntegerCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetCarComponentsCountEventHandler handler) {
    handler.onGetCarComponentsCountEvent(this);
  }
}
