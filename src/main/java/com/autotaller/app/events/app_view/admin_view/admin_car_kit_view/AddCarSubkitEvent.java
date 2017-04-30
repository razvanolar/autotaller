package com.autotaller.app.events.app_view.admin_view.admin_car_kit_view;

import com.autotaller.app.model.CarSubkitModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 01.05.2017
 */
public class AddCarSubkitEvent extends Event<AddCarSubkitEventHandler> {

  public static EventType<AddCarSubkitEventHandler> TYPE = new EventType<>();

  private CarSubkitModel carSubkit;

  public AddCarSubkitEvent(CarSubkitModel carSubkit) {
    this.carSubkit = carSubkit;
  }

  public CarSubkitModel getCarSubkits() {
    return carSubkit;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AddCarSubkitEventHandler handler) {
    handler.onAddCarSubkitEvent(this);
  }
}
