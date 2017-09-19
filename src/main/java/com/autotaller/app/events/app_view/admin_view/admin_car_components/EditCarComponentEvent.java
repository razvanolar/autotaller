package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

public class EditCarComponentEvent extends Event<EditCarComponentEventHandler> {

  public static EventType<EditCarComponentEventHandler> TYPE = new EventType<>();

  private CarComponentModel carComponent;

  public EditCarComponentEvent(CarComponentModel carComponent) {
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
  public void dispatch(EditCarComponentEventHandler handler) {
    handler.onEditCarComponentEvent(this);
  }
}
