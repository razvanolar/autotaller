package com.autotaller.app.events.app_view.admin_view.admin_car_model_view;

import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

public class DeleteCarModelEvent extends Event<DeleteCarModelEventHandler> {

  public static EventType<DeleteCarModelEventHandler> TYPE = new EventType<>();

  private CarTypeModel carType;

  public DeleteCarModelEvent(CarTypeModel carType) {
    this.carType = carType;
  }

  public CarTypeModel getCarType() {
    return carType;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(DeleteCarModelEventHandler handler) {
    handler.onDeleteCarModelEvent(this);
  }
}
