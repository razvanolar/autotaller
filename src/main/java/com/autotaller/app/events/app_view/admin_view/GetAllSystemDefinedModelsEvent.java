package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.callbacks.LoadAllSystemDefinedModelsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 05.05.2017
 */
public class GetAllSystemDefinedModelsEvent extends Event<GetAllSystemDefinedModelsEventHandler> {

  public static EventType<GetAllSystemDefinedModelsEventHandler> TYPE = new EventType<>();

  private LoadAllSystemDefinedModelsCallback callback;

  public GetAllSystemDefinedModelsEvent(LoadAllSystemDefinedModelsCallback callback) {
    this.callback = callback;
  }

  public LoadAllSystemDefinedModelsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetAllSystemDefinedModelsEventHandler handler) {
    handler.onGetAlCarDefinedModelsEvent(this);
  }
}
