package com.autotaller.app.repository;

import com.autotaller.app.model.*;
import com.autotaller.app.model.utils.ModelsDTO;
import com.autotaller.app.repository.services.UserService;
import com.autotaller.app.repository.services.cars.*;
import com.autotaller.app.repository.utils.JDBCUtil;

import java.util.List;

/**
 * Created by razvanolar on 11.04.2017
 */
public class Repository {

  private JDBCUtil jdbcUtil;
  private UserService userService;
  private CarMakesService carMakesService;
  private CarModelService carModelService;
  private CarKitsService carKitsService;
  private CarService carService;
  private CarUtilsService carUtilsService;

  private List<CarMakeModel> carMakesCache;
  private List<CarTypeModel> carTypesCache;

  private List<CarKitCategoryModel> carKitCategoriesCache;
  private List<CarKitModel> carKitsCache;
  private List<CarSubkitModel> carSubkitsCache;
  private List<FuelModel> carFuelsCache;

  public Repository() throws Exception {
    try {
      jdbcUtil = new JDBCUtil();
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    }
  }

  /*
    UserService
   */

  public int getUserIdByCredentials(String username, String password) throws Exception {
    return userService.getUserIdByCredentials(username, password);
  }


  /*
    CarMakesService
   */

  public List<CarMakeModel> getAllCarMakes() throws Exception {
    if (carMakesCache == null) {
      carMakesCache = carMakesService.getAllCarMakes();
    }
    return carMakesCache;
  }

  public void addCarMake(String name) throws Exception {
    carMakesService.addCarMake(name);
    if (carMakesCache != null) {
      carMakesCache.clear();
      carMakesCache = null;
    }
  }


  /*
    CarModelService
   */

  public List<CarTypeModel> getCarModels() throws Exception {
    if (carTypesCache == null) {
      carTypesCache = carModelService.getCarModels();
    }
    return carTypesCache;
  }

  public void addCarModel(CarTypeModel carModel) throws Exception {
    carModelService.addCarModel(carModel);
    if (carTypesCache != null) {
      carTypesCache.clear();
      carTypesCache = null;
    }
  }


  /*
    CarKitsService
   */

  public List<CarKitCategoryModel> getCarKitCategories() throws Exception {
    if (carKitCategoriesCache == null) {
      carKitCategoriesCache = carKitsService.getCarKitCategories();
    }
    return carKitCategoriesCache;
  }

  public List<CarKitModel> getCarKits() throws Exception {
    if (carKitsCache == null) {
      carKitsCache = carKitsService.getCarKits();
    }
    return carKitsCache;
  }

  public List<CarSubkitModel> getCarSubkits() throws Exception {
    if (carSubkitsCache == null) {
      carSubkitsCache = carKitsService.getCarSubkits();
    }
    return carSubkitsCache;
  }

  public void addCarKit(CarKitModel carKit) throws Exception {
    carKitsService.addCarKit(carKit);
    if (carKitsCache != null) {
      carKitsCache.clear();
      carKitsCache = null;
    }
  }

  public void addCarSubkit(CarSubkitModel carSubkit) throws Exception {
    carKitsService.addCarSubkit(carSubkit);
    if (carSubkitsCache != null) {
      carSubkitsCache.clear();
      carSubkitsCache = null;
    }
  }


  /*
    CarService
   */
  public void addCar(CarModel car, List<CarComponentModel> components) throws Exception {
    carService.addCar(car, components);
  }


  /*
    CarUtilsService
   */

  public List<FuelModel> getCarFuels() throws Exception {
    if (carFuelsCache == null) {
      carFuelsCache = carUtilsService.getCarFuels();
    }
    return carFuelsCache;
  }


  public ModelsDTO getAllDefinedModels() throws Exception {
    return new ModelsDTO(
            getCarKitCategories(),
            getCarKits(),
            getCarSubkits(),
            getAllCarMakes(),
            getCarModels(),
            getCarFuels()
    );
  }


  public void testConnection() throws Exception {
    jdbcUtil.testConnection();
    System.out.println("[ Test Connection executed successfully ]");
    initServices();
    System.out.println("[ Services were initialized ]");
//    throw new Exception("dasd");
  }

  private void initServices() throws Exception {
    userService = new UserService(jdbcUtil);
    carMakesService = new CarMakesService(jdbcUtil);
    carModelService = new CarModelService(jdbcUtil);
    carKitsService = new CarKitsService(jdbcUtil);
    carService = new CarService(jdbcUtil);
    carUtilsService = new CarUtilsService(jdbcUtil);
  }
}
