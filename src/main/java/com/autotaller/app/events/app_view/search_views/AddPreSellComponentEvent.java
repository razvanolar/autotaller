package com.autotaller.app.events.app_view.search_views;

import com.autotaller.app.model.utils.PreSellComponentModel;
import com.autotaller.app.utils.callbacks.EmptyCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 03.06.2017
 */
public class AddPreSellComponentEvent extends Event<AddPreSellComponentEventHandler> {

  public static EventType<AddPreSellComponentEventHandler> TYPE = new EventType<>();

  private PreSellComponentModel preSellComponent;
  private EmptyCallback callback;

  public AddPreSellComponentEvent(PreSellComponentModel preSellComponent, EmptyCallback callback) {
    this.preSellComponent = preSellComponent;
    this.callback = callback;
  }

  public PreSellComponentModel getPreSellComponent() {
    return preSellComponent;
  }

  public EmptyCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AddPreSellComponentEventHandler handler) {
    handler.onAddPreSellComponentEvent(this);
  }
}
