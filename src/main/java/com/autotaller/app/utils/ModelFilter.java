package com.autotaller.app.utils;

import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.model.CarKitModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 01.05.2017
 */
public class ModelFilter {

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
}
