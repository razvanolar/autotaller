package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 02.06.2017
 */
public class InjectCarKitInformationEvent extends Event<InjectCarKitInformationEventHandler> {

  public static EventType<InjectCarKitInformationEventHandler> TYPE = new EventType<>();

  private int kitId;

  public InjectCarKitInformationEvent(int kitId) {
    this.kitId = kitId;
  }

  public int getKitId() {
    return kitId;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(InjectCarKitInformationEventHandler handler) {
    handler.onInjectCarKitEvent(this);
  }
}
