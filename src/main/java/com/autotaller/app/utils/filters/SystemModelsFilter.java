package com.autotaller.app.utils.filters;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.admin_view.GetAllSystemDefinedModelsEvent;
import com.autotaller.app.model.*;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.utils.filters.car_filters.car_model_filters.CarModelFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 01.05.2017
 */
public class SystemModelsFilter {

  public static List<CarKitModel> filterCarKitsByCategory(List<CarKitModel> carKits, CarKitCategoryModel carKitCategory) {
    if (carKits == null || carKits.isEmpty() || carKitCategory == null)
      return new ArrayList<>(1);
    List<CarKitModel> result = new ArrayList<>();
    for (CarKitModel carKit : carKits) {
      if (carKitCategory.equals(carKit.getKitCategory()))
        result.add(carKit);
    }
    return result;
  }

  public static List<CarModel> filterCars(List<CarModel> cars, CarModelFilter... filters) {
    if (filters == null || filters.length == 0)
      return cars;
    List<CarModel> result = cars;
    for (CarModelFilter filter : filters) {
      result = filter.filter(result);
    }
    return result;
  }
}
