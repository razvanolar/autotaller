package com.autotaller.app.events.app_view.admin_view.admin_car_view;

import com.autotaller.app.model.SearchCarModel;
import com.autotaller.app.utils.callbacks.LoadCarsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

public class SearchCarEvent extends Event<SearchCarEventHandler> {

  public static EventType<SearchCarEventHandler> TYPE = new EventType<>();

  private SearchCarModel searchCarModel;
  private LoadCarsCallback callback;

  public SearchCarEvent(SearchCarModel searchCarModel, LoadCarsCallback callback) {
    this.searchCarModel = searchCarModel;
    this.callback = callback;
  }

  public SearchCarModel getSearchCarModel() {
    return searchCarModel;
  }

  public LoadCarsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(SearchCarEventHandler handler) {
    handler.onSearchCarEvent(this);
  }
}
