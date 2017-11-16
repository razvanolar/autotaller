package com.autotaller.app.events.app_view.search_views;

import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

public class InjectSystemModelsEvent extends Event<InjectSystemModelsEventHandler> {

  public static EventType<InjectSystemModelsEventHandler> TYPE = new EventType<>();

  private SystemModelsDTO systemModels;

  public InjectSystemModelsEvent(SystemModelsDTO systemModels) {
    this.systemModels = systemModels;
  }

  public SystemModelsDTO getSystemModels() {
    return systemModels;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(InjectSystemModelsEventHandler handler) {
    handler.onInjectSystemModelsEvent(this);
  }
}
