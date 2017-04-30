package com.autotaller.app.repository;

import com.autotaller.app.model.*;
import com.autotaller.app.repository.services.UserService;
import com.autotaller.app.repository.services.cars.CarKitsService;
import com.autotaller.app.repository.services.cars.CarMakesService;
import com.autotaller.app.repository.services.cars.CarModelService;
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
    return carMakesService.getAllCarMakes();
  }

  public void addCarMake(String name) throws Exception {
    carMakesService.addCarMake(name);
  }


  /*
    CarModelService
   */

  public List<CarTypeModel> getCarModels() throws Exception {
    return carModelService.getCarModels();
  }

  public void addCarModel(CarTypeModel carModel) throws Exception {
    carModelService.addCarModel(carModel);
  }


  /*
    CarKitsService
   */

  public List<CarKitCategoryModel> getCarKitCategories() throws Exception {
    return carKitsService.getCarKitCategories();
  }

  public List<CarKitModel> getCarKits() throws Exception {
    return carKitsService.getCarKits();
  }

  public List<CarSubkitModel> getCarSubkits() throws Exception {
    return carKitsService.getCarSubkits();
  }

  public void addCarKit(CarKitModel carKit) throws Exception {
    carKitsService.addCarKit(carKit);
  }

  public void addCarSubkit(CarSubkitModel carSubkit) throws Exception {
    carKitsService.addCarSubkit(carSubkit);
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
  }
}
