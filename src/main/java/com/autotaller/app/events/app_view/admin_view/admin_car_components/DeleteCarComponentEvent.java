package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

public class DeleteCarComponentEvent extends Event<DeleteCarComponentEventHandler> {

  public static EventType<DeleteCarComponentEventHandler> TYPE = new EventType<>();

  private CarComponentModel carComponent;

  public DeleteCarComponentEvent(CarComponentModel carComponent) {
    this.carComponent = carComponent;
  }

  public CarComponentModel getCarComponent() {
    return carComponent;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(DeleteCarComponentEventHandler handler) {
    handler.onDeleteCarComponentEvent(this);
  }
}
