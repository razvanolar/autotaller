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

  private String title;

  public AddViewToStackEvent(View view, String title) {
    this.view = view;
    this.title = title;
  }

  public View getView() {
    return view;
  }

  public String getTitle() {
    return title;
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
