package com.autotaller.app.events.app_view;

import com.autotaller.app.utils.View;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 14.04.2017
 */
public class ShowAppViewEvent extends Event<ShowAppViewEventHandler> {

  public static EventType<ShowAppViewEventHandler> TYPE = new EventType<>();

  private View appView;

  public ShowAppViewEvent(View appView) {
    this.appView = appView;
  }

  public View getAppView() {
    return appView;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(ShowAppViewEventHandler handler) {
    handler.onShowAppViewEvent(this);
  }
}
