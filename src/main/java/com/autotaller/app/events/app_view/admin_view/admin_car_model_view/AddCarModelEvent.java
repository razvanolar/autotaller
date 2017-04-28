package com.autotaller.app.events.app_view.admin_view.admin_car_model_view;

import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AddCarModelEvent extends Event<AddCarModelEventHandler> {

  public static EventType<AddCarModelEventHandler> TYPE = new EventType<>();

  private CarTypeModel carTypeModel;

  public AddCarModelEvent(CarTypeModel carTypeModel) {
    this.carTypeModel = carTypeModel;
  }

  public CarTypeModel getCarTypeModel() {
    return carTypeModel;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AddCarModelEventHandler handler) {
    handler.onAddCarModelEvent(this);
  }
}
