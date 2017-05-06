package com.autotaller.app.utils;

import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.model.CarSubkitModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  public static Map<CarKitModel, List<CarSubkitModel>> mapSubkitsByKit(List<CarSubkitModel> subkits, List<CarKitModel> kits) {
    Map<CarKitModel, List<CarSubkitModel>> map = new HashMap<>();
    for (CarKitModel kit : kits) {
      for (CarSubkitModel subkit : subkits) {
        if (subkit.getCarKit().equals(kit)) {
          List<CarSubkitModel> list = map.get(kit);
          if (list == null) {
            list = new ArrayList<>(1);
            list.add(subkit);
            map.put(kit, list);
          } else {
            list.add(subkit);
          }
        }
      }
    }
    return map;
  }
}
