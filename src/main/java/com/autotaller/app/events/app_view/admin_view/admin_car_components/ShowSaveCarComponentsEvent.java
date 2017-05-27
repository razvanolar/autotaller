package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 27.05.2017
 */
public class ShowSaveCarComponentsEvent extends Event<ShowSaveCarComponentsEventHandler> {

  public static EventType<ShowSaveCarComponentsEventHandler> TYPE = new EventType<>();

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(ShowSaveCarComponentsEventHandler handler) {
    handler.onShowSaveCarComponentsEvent(this);
  }
}
