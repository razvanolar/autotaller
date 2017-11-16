package com.autotaller.app.events.app_view.admin_view.admin_car_view;

import com.autotaller.app.model.CarModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

import java.util.List;

public class InjectCarsEvent extends Event<InjectCarsEventHandler> {

  public static EventType<InjectCarsEventHandler> TYPE = new EventType<>();

  private List<CarModel> cars;

  public InjectCarsEvent(List<CarModel> cars) {
    this.cars = cars;
  }

  public List<CarModel> getCars() {
    return cars;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(InjectCarsEventHandler handler) {
    handler.onInjectCarsEvent(this);
  }
}
