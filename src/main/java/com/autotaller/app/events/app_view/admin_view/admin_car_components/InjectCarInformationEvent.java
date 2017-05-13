package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 13.05.2017
 */
public class InjectCarInformationEvent extends Event<InjectCarInformationEventHandler> {

  public static EventType<InjectCarInformationEventHandler> TYPE = new EventType<>();

  private int carId;

  public InjectCarInformationEvent(int carId) {
    this.carId = carId;
  }

  public int getCarId() {
    return carId;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(InjectCarInformationEventHandler handler) {
    handler.onInjectCarInformationEvent(this);
  }
}
