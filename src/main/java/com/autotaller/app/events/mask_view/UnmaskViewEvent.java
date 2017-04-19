package com.autotaller.app.events.mask_view;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 14.04.2017
 */
public class UnmaskViewEvent extends Event<UnmaskViewEventHandler> {

  public static EventType<UnmaskViewEventHandler> TYPE = new EventType<>();

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(UnmaskViewEventHandler handler) {
    handler.onUnmaskViewEvent(this);
  }
}
