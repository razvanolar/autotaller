package com.autotaller.app.events.app_view.admin_view.admin_car_view;

import com.autotaller.app.model.CarModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

public class DeleteCarEvent extends Event<DeleteCarEventHandler> {

  public static EventType<DeleteCarEventHandler> TYPE = new EventType<>();

  private CarModel car;

  public DeleteCarEvent(CarModel car) {
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
  public void dispatch(DeleteCarEventHandler handler) {
    handler.onDeleteCarEvent(this);
  }
}
