package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.callbacks.LoadAllCarDefinedModelsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 14.05.2017
 */
public class GetAllCarDefinedModelsEvent extends Event<GetAllCarDefinedModelsEventHandler> {

  public static EventType<GetAllCarDefinedModelsEventHandler> TYPE = new EventType<>();

  private LoadAllCarDefinedModelsCallback callback;

  public GetAllCarDefinedModelsEvent(LoadAllCarDefinedModelsCallback callback) {
    this.callback = callback;
  }

  public LoadAllCarDefinedModelsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetAllCarDefinedModelsEventHandler handler) {
    handler.onGetAllCarDefinedModelsEvent(this);
  }
}
