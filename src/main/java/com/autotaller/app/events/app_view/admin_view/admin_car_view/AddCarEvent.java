package com.autotaller.app.events.app_view.admin_view.admin_car_view;

import com.autotaller.app.model.CarModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 07.05.2017
 */
public class AddCarEvent extends Event<AddCarEventHandler> {

  public static EventType<AddCarEventHandler> TYPE = new EventType<>();

  private CarModel car;

  public AddCarEvent(CarModel car) {
    this.car = car;
  }

  public CarModel getCar() {
    return car;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AddCarEventHandler handler) {
    handler.onAddCarEvent(this);
  }
}
