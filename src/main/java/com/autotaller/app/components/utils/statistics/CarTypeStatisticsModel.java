package com.autotaller.app.components.utils.statistics;

import com.autotaller.app.model.simple_models.SimpleCarTypeModel;

import java.util.Map;

/**
 * Created by razvanolar on 13.05.2017
 */
public class CarTypeStatisticsModel {

  // MAP(carMakeId, MAP(simple car type instance, number of cars registered for carTypeId))
  private Map<Integer, Map<SimpleCarTypeModel, Integer>> map;

  public CarTypeStatisticsModel(Map<Integer, Map<SimpleCarTypeModel, Integer>> map) {
    this.map = map;
  }

  public Map<Integer, Map<SimpleCarTypeModel, Integer>> getMap() {
    return map;
  }
}
