package com.autotaller.app.events.app_view.admin_view.admin_car_kit_view;

import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

import java.util.List;

/**
 * Created by razvanolar on 30.04.2017
 */
public class AdminLoadCarKitCategoriesEvent extends Event<AdminLoadCarKitCategoreisEventHandler> {

  public static EventType<AdminLoadCarKitCategoreisEventHandler> TYPE = new EventType<>();

  private List<CarKitCategoryModel> carKitCategories;

  public AdminLoadCarKitCategoriesEvent(List<CarKitCategoryModel> carKitCategories) {
    this.carKitCategories = carKitCategories;
  }

  public List<CarKitCategoryModel> getCarKitCategories() {
    return carKitCategories;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AdminLoadCarKitCategoreisEventHandler handler) {
    handler.onAdminLoadCarKitsEvent(this);
  }
}
