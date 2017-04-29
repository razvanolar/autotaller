package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.callbacks.LoadingCarKitCategoriesCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 30.04.2017
 */
public class GetCarKitCategoriesEvent extends Event<GetCarKitCategoriesEventHandler> {

  public static EventType<GetCarKitCategoriesEventHandler> TYPE = new EventType<>();

  private LoadingCarKitCategoriesCallback callback;

  public GetCarKitCategoriesEvent(LoadingCarKitCategoriesCallback callback) {
    this.callback = callback;
  }

  public LoadingCarKitCategoriesCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetCarKitCategoriesEventHandler handler) {
    handler.onGetCarKitCategoriesEvent(this);
  }
}
