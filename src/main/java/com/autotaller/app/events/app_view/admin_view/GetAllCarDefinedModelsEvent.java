package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.callbacks.LoadAllDefinedModelsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 05.05.2017
 */
public class GetAllCarDefinedModelsEvent extends Event<GetAllCarDefinedModelsEventHandler> {

  public static EventType<GetAllCarDefinedModelsEventHandler> TYPE = new EventType<>();

  private LoadAllDefinedModelsCallback callback;

  public GetAllCarDefinedModelsEvent(LoadAllDefinedModelsCallback callback) {
    this.callback = callback;
  }

  public LoadAllDefinedModelsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetAllCarDefinedModelsEventHandler handler) {
    handler.onGetAlCarDefinedModelsEvent(this);
  }
}
