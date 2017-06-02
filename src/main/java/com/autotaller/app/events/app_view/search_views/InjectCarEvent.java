package com.autotaller.app.events.app_view.search_views;

import com.autotaller.app.model.CarModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 02.06.2017
 */
public class InjectCarEvent extends Event<InjectCarEventHandler> {

  public static EventType<InjectCarEventHandler> TYPE = new EventType<>();

  private CarModel car;

  public InjectCarEvent(CarModel car) {
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
  public void dispatch(InjectCarEventHandler handler) {
    handler.onInjectCarEvent(this);
  }
}
