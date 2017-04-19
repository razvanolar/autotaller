package com.autotaller.app.events.view_stack;

import com.autotaller.app.utils.View;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 12.04.2017
 */
public class AddViewToStackEvent extends Event<AddViewToStackEventHandler> {

  public static EventType<AddViewToStackEventHandler> TYPE = new EventType<>();

  private View view;

  public AddViewToStackEvent(View view) {
    this.view = view;
  }

  public View getView() {
    return view;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AddViewToStackEventHandler handler) {
    handler.onAddViewToStackEvent(this);
  }
}
