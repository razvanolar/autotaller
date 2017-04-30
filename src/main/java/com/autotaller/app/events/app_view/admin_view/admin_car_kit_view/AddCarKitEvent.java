package com.autotaller.app.events.app_view.admin_view.admin_car_kit_view;

import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 30.04.2017
 */
public class AddCarKitEvent extends Event<AddCarKitEventHandler> {

  public static EventType<AddCarKitEventHandler> TYPE = new EventType<>();

  private CarKitModel carKit;

  public AddCarKitEvent(CarKitModel carKit) {
    this.carKit = carKit;
  }

  public CarKitModel getCarKit() {
    return carKit;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AddCarKitEventHandler handler) {
    handler.onAddCarKitEvent(this);
  }
}
