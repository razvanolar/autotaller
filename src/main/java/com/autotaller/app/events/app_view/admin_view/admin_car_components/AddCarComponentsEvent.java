package com.autotaller.app.events.app_view.admin_view.admin_car_components;

import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

import java.util.List;

/**
 * Created by razvanolar on 27.05.2017
 */
public class AddCarComponentsEvent extends Event<AddCarComponentsEventHandler> {

  public static EventType<AddCarComponentsEventHandler> TYPE = new EventType<>();

  private List<CarComponentModel> carComponents;

  public AddCarComponentsEvent(List<CarComponentModel> carComponents) {
    this.carComponents = carComponents;
  }

  public List<CarComponentModel> getCarComponents() {
    return carComponents;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AddCarComponentsEventHandler handler) {
    handler.onAddCarComponentsEvent(this);
  }
}
