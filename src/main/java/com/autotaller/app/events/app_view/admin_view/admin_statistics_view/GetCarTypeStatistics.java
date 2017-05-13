package com.autotaller.app.events.app_view.admin_view.admin_statistics_view;

import com.autotaller.app.utils.callbacks.LoadCarTypeStatisticsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 13.05.2017
 */
public class GetCarTypeStatistics extends Event<GetCarTypeStatisticsHandler> {

  public static EventType<GetCarTypeStatisticsHandler> TYPE = new EventType<>();

  private LoadCarTypeStatisticsCallback callback;

  public GetCarTypeStatistics(LoadCarTypeStatisticsCallback callback) {
    this.callback = callback;
  }

  public LoadCarTypeStatisticsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetCarTypeStatisticsHandler handler) {
    handler.onGetStatistincForCarTypesEvent(this);
  }
}
