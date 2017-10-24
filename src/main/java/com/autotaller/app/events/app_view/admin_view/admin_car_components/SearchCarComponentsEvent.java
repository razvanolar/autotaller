package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.model.SearchComponentModel;
import com.autotaller.app.utils.callbacks.LoadCarComponentsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

public class SearchCarComponentsEvent extends Event<SearchCarComponentsEventHandler> {

  public static EventType<SearchCarComponentsEventHandler> TYPE = new EventType<>();

  private SearchComponentModel searchModel;
  private LoadCarComponentsCallback callback;

  public SearchCarComponentsEvent(SearchComponentModel searchModel, LoadCarComponentsCallback callback) {
    this.searchModel = searchModel;
    this.callback = callback;
  }

  public SearchComponentModel getSearchModel() {
    return searchModel;
  }

  public LoadCarComponentsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(SearchCarComponentsEventHandler handler) {
    handler.onSearchCarComponentsEvent(this);
  }
}
