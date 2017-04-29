package com.autotaller.app.events.app_view.admin_view.admin_car_kit_view;

import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

import java.util.List;

/**
 * Created by razvanolar on 30.04.2017
 */
public class AdminLoadCarKitsEvent extends Event<AdminLoadCarKitsEventHandler> {

  public static EventType<AdminLoadCarKitsEventHandler> TYPE = new EventType<>();

  private List<CarKitModel> carKits;

  public AdminLoadCarKitsEvent(List<CarKitModel> carKits) {
    this.carKits = carKits;
  }

  public List<CarKitModel> getCarKits() {
    return carKits;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AdminLoadCarKitsEventHandler handler) {
    handler.onAdminLoadCarKitsEvent(this);
  }
}
