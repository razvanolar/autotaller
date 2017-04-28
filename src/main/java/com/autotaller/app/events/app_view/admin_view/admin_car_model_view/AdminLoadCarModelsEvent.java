package com.autotaller.app.events.app_view.admin_view.admin_car_model_view;

import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

import java.util.List;

/**
 * Created by razvanolar on 28.04.2017
 */
public class AdminLoadCarModelsEvent extends Event<AdminLoadCarModelsEventHandler> {

  public static EventType<AdminLoadCarModelsEventHandler> TYPE = new EventType<>();

  private List<CarTypeModel> carModels;

  public AdminLoadCarModelsEvent(List<CarTypeModel> carModels) {
    this.carModels = carModels;
  }

  public List<CarTypeModel> getCarModels() {
    return carModels;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AdminLoadCarModelsEventHandler handler) {
    handler.onAdminLoadCarModelsEvent(this);
  }
}
