package com.autotaller.app.events.app_view.admin_view.admin_car_kit_view;

import com.autotaller.app.model.CarSubkitModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

import java.util.List;

/**
 * Created by razvanolar on 30.04.2017
 */
public class AdminLoadCarSubkitsEvent extends Event<AdminLoadCarSubkitsEventHandler> {

  public static EventType<AdminLoadCarSubkitsEventHandler> TYPE = new EventType<>();

  private List<CarSubkitModel> carSubkits;

  public AdminLoadCarSubkitsEvent(List<CarSubkitModel> carSubkits) {
    this.carSubkits = carSubkits;
  }

  public List<CarSubkitModel> getCarSubkits() {
    return carSubkits;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AdminLoadCarSubkitsEventHandler handler) {
    handler.onAdminLoadCarSubkitsEvent(this);
  }
}
