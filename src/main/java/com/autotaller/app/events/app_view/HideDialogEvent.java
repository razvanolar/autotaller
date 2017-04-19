package com.autotaller.app.events.app_view;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 19.04.2017
 */
public class HideDialogEvent extends Event<HideDialogEventHandler> {

  public static EventType<HideDialogEventHandler> TYPE = new EventType<>();

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(HideDialogEventHandler handler) {
    handler.onHideDialogEvent(this);
  }
}
