package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.callbacks.LoadCarComponentsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 13.05.2017
 */
public class GetCarComponentsEvent extends Event<GetCarComponentsEventHandler> {

  public static EventType<GetCarComponentsEventHandler> TYPE = new EventType<>();

  private LoadCarComponentsCallback callback;
  private int limit;
  private int offset;

  public GetCarComponentsEvent(LoadCarComponentsCallback callback, int limit, int offset) {
    this.callback = callback;
    this.limit = limit;
    this.offset = offset;
  }

  public LoadCarComponentsCallback getCallback() {
    return callback;
  }

  public int getLimit() {
    return limit;
  }

  public int getOffset() {
    return offset;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetCarComponentsEventHandler handler) {
    handler.onGetCarComponentsEvent(this);
  }
}
