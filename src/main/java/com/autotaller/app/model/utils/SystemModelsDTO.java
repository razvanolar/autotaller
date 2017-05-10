package com.autotaller.app.model.utils;

import com.autotaller.app.model.*;

import java.util.*;

/**
 * Created by razvanolar on 05.05.2017
 */
public class SystemModelsDTO {

  private List<CarKitCategoryModel> carKitCategories;
  private List<CarKitModel> carKits;
  private List<CarSubkitModel> carSubkits;
  private List<CarMakeModel> carMakes;
  private List<CarTypeModel> carTypes;
  private List<FuelModel> fuels;

  private Map<CarMakeModel, List<CarTypeModel>> carMakeTypeMap;
  private Map<CarKitCategoryModel, List<CarKitModel>> carCategoryKitMap;
  private Map<CarKitModel, List<CarSubkitModel>> carKitSubkitMap;

  public SystemModelsDTO(List<CarKitCategoryModel> carKitCategories,
                         List<CarKitModel> carKits,
                         List<CarSubkitModel> carSubkits,
                         List<CarMakeModel> carMakes,
                         List<CarTypeModel> carTypes,
                         List<FuelModel> fuels) {
    this.carKitCategories = carKitCategories;
    this.carKits = carKits;
    this.carSubkits = carSubkits;
    this.carMakes = carMakes;
    this.carTypes = carTypes;
    this.fuels = fuels;

    this.carMakeTypeMap = new HashMap<>();
    this.carCategoryKitMap = new HashMap<>();
    this.carKitSubkitMap = new HashMap<>();

    computeCarMakeTypeMap();
    computeCarCategoryKitMap();
    computeCarKitSubkitMap();
  }

  public List<CarKitCategoryModel> getCarKitCategories() {
    return carKitCategories;
  }

  public List<CarKitModel> getCarKits() {
    return carKits;
  }

  public List<CarSubkitModel> getCarSubkits() {
    return carSubkits;
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

  public List<CarTypeModel> getCarTypesByMake(CarMakeModel carMake) {
    return carMakeTypeMap.get(carMake);
  }

  public List<CarKitModel> getCarKitByCategory(CarKitCategoryModel carKitCategory) {
    return carCategoryKitMap.get(carKitCategory);
  }

  public List<CarSubkitModel> getCarSubkitByKit(CarKitModel carKit) {
    return carKitSubkitMap.get(carKit);
  }

  public CarTypeModel getCarTypeModelById(int carTypeId) {
    for (CarTypeModel carType : carTypes) {
      if (carType.getId() == carTypeId)
        return carType;
    }
    return null;
  }

  public FuelModel getFuelById(int fuelId) {
    for (FuelModel fuel : fuels) {
      if (fuel.getId() == fuelId)
        return fuel;
    }
    return null;
  }

  private void computeCarMakeTypeMap() {
    for (CarMakeModel carMake : carMakes) {
      List<CarTypeModel> carTypesList = new ArrayList<>();
      for (CarTypeModel carType : carTypes) {
        if (carType.getCarMake().equals(carMake)) {
          carTypesList.add(carType);
        }
      }
      carMakeTypeMap.put(carMake, carTypesList);
    }

    for (CarMakeModel carMake : carMakeTypeMap.keySet()) {
      List<CarTypeModel> carTypesList = carMakeTypeMap.get(carMake);
      carTypesList.sort(Comparator.comparing(CarTypeModel::getName));
    }
  }

  private void computeCarCategoryKitMap() {
    for (CarKitCategoryModel carKitCategory : carKitCategories) {
      List<CarKitModel> carKitsList = new ArrayList<>();
      for (CarKitModel carKit : carKits) {
        if (carKit.getKitCategory().equals(carKitCategory)) {
          carKitsList.add(carKit);
        }
      }
      carCategoryKitMap.put(carKitCategory, carKitsList);
    }

    for (CarKitCategoryModel carKitCategory : carCategoryKitMap.keySet()) {
      List<CarKitModel> carKitsList = carCategoryKitMap.get(carKitCategory);
      carKitsList.sort(Comparator.comparing(CarKitModel::getName));
    }
  }

  private void computeCarKitSubkitMap() {
    for (CarKitModel carKit : carKits) {
      List<CarSubkitModel> carSubkitsList = new ArrayList<>();
      for (CarSubkitModel carSubkit : carSubkits) {
        if (carSubkit.getCarKit().equals(carKit)) {
          carSubkitsList.add(carSubkit);
        }
      }
      carKitSubkitMap.put(carKit, carSubkitsList);
    }

    for (CarKitModel carKit : carKitSubkitMap.keySet()) {
      List<CarSubkitModel> carSubkitsList = carKitSubkitMap.get(carKit);
      carSubkitsList.sort(Comparator.comparing(CarSubkitModel::getName));
    }
  }
}
