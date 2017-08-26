package com.autotaller.app.events.app_view.admin_view.admin_car_make_view;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

public class DeleteCarMakeEvent extends Event<DeleteCarMakeEventHandler> {

  public static EventType<DeleteCarMakeEventHandler> TYPE = new EventType<>();

  private CarMakeModel carMake;

  public DeleteCarMakeEvent(CarMakeModel carMake) {
    this.carMake = carMake;
  }

  public CarMakeModel getCarMake() {
    return carMake;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(DeleteCarMakeEventHandler handler) {
    handler.onDeleteCarMakeEvent(this);
  }
}
