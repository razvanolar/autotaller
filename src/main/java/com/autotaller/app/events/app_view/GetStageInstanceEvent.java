package com.autotaller.app.events.app_view;

import com.autotaller.app.utils.callbacks.GetStageInstanceCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 27.05.2017
 */
public class GetStageInstanceEvent extends Event<GetStageInstanceEventHandler> {

  public static EventType<GetStageInstanceEventHandler> TYPE = new EventType<>();

  private GetStageInstanceCallback callback;

  public GetStageInstanceEvent(GetStageInstanceCallback callback) {
    this.callback = callback;
  }

  public GetStageInstanceCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetStageInstanceEventHandler handler) {
    handler.onGetStageInstanceEvent(this);
  }
}
