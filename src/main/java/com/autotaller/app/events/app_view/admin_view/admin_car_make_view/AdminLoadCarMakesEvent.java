package com.autotaller.app.events.app_view.admin_view.admin_car_make_view;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

import java.util.List;

/**
 * Created by razvanolar on 19.04.2017
 */
public class AdminLoadCarMakesEvent extends Event<AdminLoadCarMakesEventHandler> {

  public static EventType<AdminLoadCarMakesEventHandler> TYPE = new EventType<>();

  private List<CarMakeModel> carMakeModels;

  public AdminLoadCarMakesEvent(List<CarMakeModel> carMakeModels) {
    this.carMakeModels = carMakeModels;
  }

  public List<CarMakeModel> getCarMakeModels() {
    return carMakeModels;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AdminLoadCarMakesEventHandler handler) {
    handler.onAdminLoadCarMakesEvent(this);
  }
}
