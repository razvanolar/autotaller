package com.autotaller.app.events.app_view.search_views;

import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 02.06.2017
 */
public class InjectCarTypeEvent extends Event<InjectCarTypeEventHandler> {

  public static EventType<InjectCarTypeEventHandler> TYPE = new EventType<>();

  private CarTypeModel carType;

  public InjectCarTypeEvent(CarTypeModel carType) {
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
  public void dispatch(InjectCarTypeEventHandler handler) {
    handler.onInjectCarTypeEvent(this);
  }
}
