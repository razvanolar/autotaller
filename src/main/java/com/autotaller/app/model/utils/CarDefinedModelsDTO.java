package com.autotaller.app.model.utils;

import com.autotaller.app.model.CarBodyTypeModel;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.model.FuelModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by razvanolar on 14.05.2017
 */
public class CarDefinedModelsDTO {

  private List<CarMakeModel> carMakes;
  private List<CarTypeModel> carTypes;
  private List<FuelModel> fuels;
  private List<CarBodyTypeModel> bodyTypes;

  private Map<CarMakeModel, List<CarTypeModel>> carMakeTypeListMap;

  public CarDefinedModelsDTO(List<CarMakeModel> carMakes, List<CarTypeModel> carTypes, List<FuelModel> fuels, List<CarBodyTypeModel> bodyTypes) {
    this.carMakes = carMakes;
    this.carTypes = carTypes;
    this.fuels = fuels;
    this.bodyTypes = bodyTypes;

    initCarMakeTypeListMap();
  }

  public List<CarMakeModel> getCarMakes() {
    return carMakes;
  }

  public List<CarTypeModel> getCarTypes() {
    return carTypes;
  }

  public List<FuelModel> getFuels() {
    return fuels;
  }

  public List<CarBodyTypeModel> getBodyTypes() {
    return bodyTypes;
  }

  public List<CarTypeModel> getCarTypesByMake(CarMakeModel carMake) {
    return carMakeTypeListMap.get(carMake);
  }

  private void initCarMakeTypeListMap() {
    carMakeTypeListMap = new HashMap<>();
    for (CarTypeModel carType : carTypes) {
      List<CarTypeModel> carTypesList = carMakeTypeListMap.get(carType.getCarMake());
      if (carTypesList == null) {
        carTypesList = new ArrayList<>();
        carTypesList.add(carType);
        carMakeTypeListMap.put(carType.getCarMake(), carTypesList);
      } else {
        carTypesList.add(carType);
      }
    }
  }
}
