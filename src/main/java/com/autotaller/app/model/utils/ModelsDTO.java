package com.autotaller.app.model.utils;

import com.autotaller.app.model.*;

import java.util.List;

/**
 * Created by razvanolar on 05.05.2017
 */
public class ModelsDTO {

  private List<CarKitCategoryModel> carKitCategories;
  private List<CarKitModel> carKits;
  private List<CarSubkitModel> carSubkits;
  private List<CarMakeModel> carMakes;
  private List<CarTypeModel> carTypes;
  private List<FuelModel> fuels;

  public ModelsDTO(List<CarKitCategoryModel> carKitCategories,
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
}
