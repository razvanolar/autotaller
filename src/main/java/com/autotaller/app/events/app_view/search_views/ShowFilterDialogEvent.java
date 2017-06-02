package com.autotaller.app.events.app_view.search_views;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 02.06.2017
 */
public class ShowFilterDialogEvent extends Event<ShowFilterDialogEventHandler> {

  public static EventType<ShowFilterDialogEventHandler> TYPE = new EventType<>();

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(ShowFilterDialogEventHandler handler) {
    handler.onShowFilterDialogEvent(this);
  }
}
