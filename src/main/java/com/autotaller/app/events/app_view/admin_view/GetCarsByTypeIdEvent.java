package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.callbacks.LoadCarsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 13.05.2017
 */
public class GetCarsByTypeIdEvent extends Event<GetCarsByTypeIdEventHandler> {

  public static EventType<GetCarsByTypeIdEventHandler> TYPE = new EventType<>();

  private int carTypeId;
  private LoadCarsCallback callback;

  public GetCarsByTypeIdEvent(int carTypeId, LoadCarsCallback callback) {
    this.carTypeId = carTypeId;
    this.callback =callback;
  }

  public int getCarTypeId() {
    return carTypeId;
  }

  public LoadCarsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetCarsByTypeIdEventHandler handler) {
    handler.onGetCarsByTypeIdEvent(this);
  }
}
