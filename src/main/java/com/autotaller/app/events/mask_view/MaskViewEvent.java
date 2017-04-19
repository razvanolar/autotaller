package com.autotaller.app.events.mask_view;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 14.04.2017
 */
public class MaskViewEvent extends Event<MaskViewEventHandler> {

  public static EventType<MaskViewEventHandler> TYPE = new EventType<>();

  private String message;

  public MaskViewEvent(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(MaskViewEventHandler handler) {
    handler.onMaskViewEvent(this);
  }
}
