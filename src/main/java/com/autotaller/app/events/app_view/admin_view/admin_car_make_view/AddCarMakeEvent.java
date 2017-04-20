package com.autotaller.app.events.app_view.admin_view.admin_car_make_view;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AddCarMakeEvent extends Event<AddCarMakeEventHandler> {

  public static EventType<AddCarMakeEventHandler> TYPE = new EventType<>();

  private String carMakeName;

  public AddCarMakeEvent(String carMakeName) {
    this.carMakeName = carMakeName;
  }

  public String getCarMakeName() {
    return carMakeName;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AddCarMakeEventHandler handler) {
    handler.onAddCarMakeEvent(this);
  }
}
