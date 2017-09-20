package com.autotaller.app.repository;

import com.autotaller.app.components.utils.statistics.CarTypeStatisticsModel;
import com.autotaller.app.model.*;
import com.autotaller.app.model.notifications.DetailedSellModel;
import com.autotaller.app.model.notifications.SimpleSellModel;
import com.autotaller.app.model.utils.CarDefinedModelsDTO;
import com.autotaller.app.model.utils.PreSellComponentModel;
import com.autotaller.app.model.utils.SaveCarResult;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.repository.services.UserService;
import com.autotaller.app.repository.services.cars.*;
import com.autotaller.app.repository.utils.CarStatus;
import com.autotaller.app.repository.utils.ImageStatus;
import com.autotaller.app.repository.utils.JDBCUtil;
import com.autotaller.app.repository.utils.RepoFileUtil;
import com.autotaller.app.utils.SellModelStatus;

import java.io.File;
import java.util.List;

/**
 * Created by razvanolar on 11.04.2017
 */
public class Repository {

  public String APP_VARIABLE_NAME = "AUTOTALLER";

  private JDBCUtil jdbcUtil;
  private UserService userService;
  private CarMakesService carMakesService;
  private CarModelService carModelService;
  private CarKitsService carKitsService;
  private CarService carService;
  private CarComponentsService carComponentsService;
  private CarStatisticsService carStatisticsService;
  private CarUtilsService carUtilsService;

  private RepoFileUtil fileUtil;

  private List<CarMakeModel> carMakesCache;
  private List<CarTypeModel> carTypesCache;

  private List<CarKitCategoryModel> carKitCategoriesCache;
  private List<CarKitModel> carKitsCache;
  private List<CarSubkitModel> carSubkitsCache;
  private List<FuelModel> carFuelsCache;
  private List<CarBodyTypeModel> carBodyTypesCache;

  public Repository() throws Exception {
    try {
      jdbcUtil = new JDBCUtil();
      fileUtil = new RepoFileUtil(System.getenv(APP_VARIABLE_NAME));
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    }
  }

  /*
    UserService
   */

  public UserModel getUserIdByCredentials(String username, String password) throws Exception {
    return userService.getUserIdByCredentials(username, password);
  }

  public boolean checkUserPassword(int userId, String password) throws Exception {
    return userService.checkUserPassword(userId, password);
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

  public void deleteCarMake(CarMakeModel carMake) throws Exception {
    carMakesService.deleteCarMake(carMake);
    if (carMakesCache != null) {
      carMakesCache.clear();
      carMakesCache = null;
    }
    if (carTypesCache != null) {
      carTypesCache.clear();
      carTypesCache = null;
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

  public void deleteCarType(CarTypeModel carType) throws Exception {
    carModelService.deleteCarType(carType);
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

  public CarSubkitModel getCarSubkitById(int carSubkitId) throws Exception {
    List<CarSubkitModel> carSubkits = getCarSubkits();
    for (CarSubkitModel subkit : carSubkits) {
      if (subkit.getId() == carSubkitId)
        return subkit;
    }
    return null;
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
  public List<CarModel> getCars() throws Exception {
    return carService.getCars(getAllDefinedModels());
  }

  public List<CarModel> getCarsByTypeId(int carTypeId) throws Exception {
    return carService.getCarsByTypeId(carTypeId, getAllDefinedModels());
  }

  public CarModel getCarById(int carId) throws Exception {
    return carService.getCarById(carId, getAllDefinedModels());
  }

  public SaveCarResult addCar(CarModel car, List<File> images) throws Exception {
    int carId = carService.addCar(car);
    CarModel savedCar = getCarById(carId);
    CarStatus carStatus = savedCar != null ? CarStatus.SUCCESS_SAVE : CarStatus.FAILED_SAVE;
    ImageStatus imageStatus;
    try {
      if (!fileUtil.isHomeSet()) {
        throw new Exception("Directorul %AUTOTALLER% nu a putut fi gasit");
      }
      if (images == null || images.isEmpty()) {
        imageStatus = null;
      } else {
        fileUtil.copyCarImages(carId, images);
        imageStatus = ImageStatus.SUCCESS_IMAGE_SAVE;
      }
    } catch (Exception e) {
      e.printStackTrace();
      imageStatus = ImageStatus.FAILED_IMAGE_SAVE;
    }
    return new SaveCarResult(savedCar, carStatus, imageStatus);
  }

  public void deleteCar(CarModel car) throws Exception {
    carService.deleteCar(car);
  }


  /*
    CarComponentsService
   */
  public int getCarComponentsCount() throws Exception {
    return carComponentsService.getCarComponentsCount();
  }

  public List<CarComponentModel> getCarComponents(int limit, int offset) throws Exception {
    return carComponentsService.getCarComponents(limit, offset);
  }

  public List<CarComponentModel> getCarComponentsByCarId(int carId) throws Exception {
    return carComponentsService.getComponentsByCarId(carId);
  }

  public List<CarComponentModel> getCarComponentsByCarAndKitId(int carId, int kitId) throws Exception {
    return carComponentsService.getComponentsByCarAndKitId(carId, kitId);
  }

  public CarComponentModel getCarComponentById(int componentId) throws Exception {
    return carComponentsService.getCarComponentById(componentId);
  }

  public void addComponents(List<CarComponentModel> components) throws Exception {
    carComponentsService.addComponents(components);
  }

  public void addPresellComponent(PreSellComponentModel preSellComponent, int userId) throws Exception {
    carComponentsService.addPreSellComponent(preSellComponent, userId);
  }

  public List<SimpleSellModel> getSimpleSellModels(SellModelStatus status) throws Exception {
    return carComponentsService.getSimpleSellModels(status);
  }

  public DetailedSellModel getDetailedSellModel(SimpleSellModel sellModel) throws Exception {
    CarComponentModel carComponent = getCarComponentById(sellModel.getComponentId());
    return new DetailedSellModel(
            sellModel.getId(), carComponent, getCarById(carComponent.getCarId()),
            getCarSubkitById(carComponent.getCarSubkitId()), sellModel.getSoldPieces(), sellModel.getStockPrice(),
            sellModel.getSoldPrice(), sellModel.getSoldDate(), sellModel.getUserId(), sellModel.getUserName(),
            sellModel.getStatus()
    );
  }

  public void confirmSellNotification(SimpleSellModel sellModel) throws Exception {
    CarComponentModel component = getCarComponentById(sellModel.getComponentId());
    if (component.getLeftPieces() < sellModel.getSoldPieces()) {
      throw new Exception("Nu exista suficiente bucati pe stoc pentru a valida vanzarea");
    }
    carComponentsService.confirmSellModel(sellModel);
  }

  public void cancelSellNotification(SimpleSellModel sellModel) throws Exception {
    carComponentsService.cancelSellModel(sellModel.getId());
  }

  public void editComponent(CarComponentModel carComponent) throws Exception {
    carComponentsService.editComponent(carComponent);
  }

  public void deleteComponent(CarComponentModel carComponent) throws Exception {
    carComponentsService.deleteComponent(carComponent);
  }




  /*
    CarStatisticsService
   */
  public CarTypeStatisticsModel getCarTypeStatistics() throws Exception {
    return carStatisticsService.getCarTypeStatistics();
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

  public List<CarBodyTypeModel> getCarBodyTypes() throws Exception {
    if (carBodyTypesCache == null) {
      carBodyTypesCache = carUtilsService.getCarBodyTypes();
    }
    return carBodyTypesCache;
  }


  public SystemModelsDTO getAllDefinedModels() throws Exception {
    return new SystemModelsDTO(
            getCarKitCategories(),
            getCarKits(),
            getCarSubkits(),
            getAllCarMakes(),
            getCarModels(),
            getCarFuels(),
            getCarBodyTypes()
    );
  }

  public CarDefinedModelsDTO getCarDefinedModels() throws Exception {
    return new CarDefinedModelsDTO(getAllCarMakes(), getCarModels(), getCarFuels(), getCarBodyTypes());
  }


  public List<File> getCarImageFiles(int carId) throws Exception {
    return fileUtil.getCarFiles(carId);
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
    carService = new CarService(jdbcUtil);
    carModelService = new CarModelService(jdbcUtil, carService);
    carMakesService = new CarMakesService(jdbcUtil, carModelService);
    carKitsService = new CarKitsService(jdbcUtil);
    carUtilsService = new CarUtilsService(jdbcUtil);
    carStatisticsService = new CarStatisticsService(jdbcUtil);
    carComponentsService = new CarComponentsService(jdbcUtil);

    //init all the caches
    getAllDefinedModels();
  }
}
