package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.utils.callbacks.LoadCarComponentsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 02.06.2017
 */
public class GetCarComponentsByCarAndKitIdEvent extends Event<GetCarComponentsByCarAndKitIdEventHandler> {

  public static EventType<GetCarComponentsByCarAndKitIdEventHandler> TYPE = new EventType<>();

  private int carId;
  private int kitId;
  private LoadCarComponentsCallback callback;

  public GetCarComponentsByCarAndKitIdEvent(int carId, int kitId, LoadCarComponentsCallback callback) {
    this.carId = carId;
    this.kitId = kitId;
    this.callback = callback;
  }

  public int getCarId() {
    return carId;
  }

  public int getKitId() {
    return kitId;
  }

  public LoadCarComponentsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetCarComponentsByCarAndKitIdEventHandler handler) {
    handler.onGetCarComponentsByCarAndKitIdEvent(this);
  }
}
