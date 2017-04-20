package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.callbacks.LoadingCarModelsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 20.04.2017
 */
public class GetCarModelsEvent extends Event<GetCarModelsEventHandler> {

  public static EventType<GetCarModelsEventHandler> TYPE = new EventType<>();

  private LoadingCarModelsCallback callback;

  public GetCarModelsEvent(LoadingCarModelsCallback callback) {
    this.callback = callback;
  }

  public LoadingCarModelsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetCarModelsEventHandler handler) {
    handler.onGetCarModelsEvent(this);
  }
}
