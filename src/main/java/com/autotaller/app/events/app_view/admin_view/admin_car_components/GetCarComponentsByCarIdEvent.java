package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.utils.callbacks.LoadCarComponentsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 13.05.2017
 */
public class GetCarComponentsByCarIdEvent extends Event<GetCarComponentsByCarIdEventHandler> {

  public static EventType<GetCarComponentsByCarIdEventHandler> TYPE = new EventType<>();

  private int carId;
  private LoadCarComponentsCallback callback;

  public GetCarComponentsByCarIdEvent(int carId, LoadCarComponentsCallback callback) {
    this.carId = carId;
    this.callback = callback;
  }

  public int getCarId() {
    return carId;
  }

  public LoadCarComponentsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetCarComponentsByCarIdEventHandler handler) {
    handler.onGetCarComponentsEvent(this);
  }
}
