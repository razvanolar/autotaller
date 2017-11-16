package com.autotaller.app.repository.services.cars;

import com.autotaller.app.model.*;
import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;
import com.autotaller.app.utils.CarWheelSideType;
import com.autotaller.app.utils.StockType;
import com.autotaller.app.utils.StringValidator;
import com.autotaller.app.utils.UsageStateType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 06.05.2017
 */
public class CarUtilsService extends GenericService {

  public CarUtilsService(JDBCUtil jdbcUtil) {
    super(jdbcUtil);
  }

  public List<FuelModel> getCarFuels() throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = "SELECT id, name FROM car_fuels";
      statement = connection.prepareStatement(query);
      rs = statement.executeQuery();
      List<FuelModel> result = new ArrayList<>();
      while (rs.next()) {
        result.add(new FuelModel(rs.getInt(1), rs.getString(2)));
      }
      return result;
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, rs);
    }
  }

  public List<CarBodyTypeModel> getCarBodyTypes() throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      statement = connection.prepareStatement("SELECT id, name FROM car_body_types");
      rs = statement.executeQuery();
      List<CarBodyTypeModel> result = new ArrayList<>();
      while (rs.next()) {
        result.add(new CarBodyTypeModel(rs.getInt(1), rs.getString(2)));
      }
      return result;
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, rs);
    }
  }

  public String concatenateSearchConditions(List<String> conditions) {
    if (conditions == null || conditions.isEmpty())
      return "";
    StringBuilder builder = new StringBuilder();
    builder.append(conditions.get(0));
    if (conditions.size() == 1)
      return builder.toString();
    builder.append(" AND ");
    for (int i = 1; i < conditions.size(); i++) {
      builder.append(conditions.get(i));
      if (i < conditions.size() - 1)
        builder.append(" AND ");
    }
    return builder.toString();
  }

  public List<String> computeSearchConditions(SearchCarModel searchModel) {
    if (searchModel == null)
      return new ArrayList<>();
    List<String> list = new ArrayList<>();
    if (searchModel.getCarType() != null && searchModel.getCarType().getId() > 0)
      list.add("c.model_id = " + searchModel.getCarType().getId());
    if (searchModel.getParkNumber() > 0)
      list.add("c.parkNumber = " + searchModel.getParkNumber());
    if (searchModel.getProductionYear() > 0)
      list.add("YEAR(c.production_year) = " + searchModel.getProductionYear());
    if (searchModel.getPowerFrom() > 0)
      list.add("c.kw >= " + (searchModel.isUseKWUnit() ? searchModel.getPowerFrom() : searchModel.getPowerFrom() * (float) 1.34102));
    if (searchModel.getPowerTo() > 0)
      list.add("c.kw <= " + (searchModel.isUseKWUnit() ? searchModel.getPowerTo() : searchModel.getPowerTo() * (float) 1.34102));
    if (searchModel.getKmFrom() > 0)
      list.add("c.km >= " + searchModel.getKmFrom());
    if (searchModel.getKmTo() > 0)
      list.add("c.km <= " + searchModel.getKmTo());
    if (searchModel.getCapacityFrom() > 0)
      list.add("c.cilindrical_capacity >= " + searchModel.getCapacityFrom());
    if (searchModel.getCapacityTo() > 0)
      list.add("c.cilindrical_capacity <= " + searchModel.getCapacityTo());
    if (searchModel.getPriceFrom() > 0)
      list.add("c.price >= " + searchModel.getPriceFrom());
    if (searchModel.getPriceTo() > 0)
      list.add("c.price <= " + searchModel.getPriceTo());

    StringBuilder builder = new StringBuilder();
    List<CarWheelSideType> wheelSideList = searchModel.getWheelSideList();
    if (wheelSideList != null && !wheelSideList.isEmpty()) {
      builder.append("(");
      for (int i = 0; i < wheelSideList.size(); i++) {
        builder.append("'").append(wheelSideList.get(i).getValue()).append("'");
        if (i < wheelSideList.size() - 1)
          builder.append(", ");
      }
      builder.append(")");
      list.add("c.wheel_side IN " + builder.toString());
    }
    builder.setLength(0);

    List<FuelModel> fuelList = searchModel.getFuelList();
    if (fuelList != null && !fuelList.isEmpty()) {
      builder.append("(");
      for (int i = 0; i < fuelList.size(); i++) {
        builder.append(fuelList.get(i).getId());
        if (i < fuelList.size() - 1)
          builder.append(", ");
      }
      builder.append(")");
      list.add("c.fuel_id IN " + builder.toString());
    }
    builder.setLength(0);

    List<CarBodyTypeModel> bodyTypeList = searchModel.getBodyTypeList();
    if (bodyTypeList != null && !bodyTypeList.isEmpty()) {
      builder.append("(");
      for (int i = 0; i < bodyTypeList.size(); i++) {
        builder.append("'").append(bodyTypeList.get(i).getId()).append("'");
        if (i < bodyTypeList.size() - 1)
          builder.append(", ");
      }
      builder.append(")");
      list.add("c.body_type_id IN " + builder.toString());
    }

    return list;
  }

  public List<String> computeSearchConditions(SearchComponentModel searchModel) {
    if (searchModel == null)
      return new ArrayList<>();
    List<String> list = new ArrayList<>();
    if (!StringValidator.isNullOrEmpty(searchModel.getName()))
      list.add("cc.component_name LIKE '%" + searchModel.getName() + "%'");
    if (!StringValidator.isNullOrEmpty(searchModel.getCode()))
      list.add("cc.code LIKE '%" + searchModel.getCode() + "%'");
    if (searchModel.getMinPrice() > 0)
      list.add("cc.price >= " + searchModel.getMinPrice());
    if (searchModel.getMaxPrice() > 0)
      list.add("cc.price <= " + searchModel.getMaxPrice());

    List<UsageStateType> usageList = searchModel.getUsageList();
    if (usageList != null && !usageList.isEmpty() && usageList.size() != UsageStateType.values().length) {
      StringBuilder ids = new StringBuilder("(");
      for (int i = 0; i < usageList.size(); i++) {
        ids.append(usageList.get(i).getId());
        if (i < usageList.size() - 1)
          ids.append(", ");
      }
      ids.append(")");
      list.add("cc.usage_state IN " + ids.toString());
    }

    List<StockType> stockList = searchModel.getStockList();
    if (stockList != null && !stockList.isEmpty() && stockList.size() != StockType.values().length) {
      StringBuilder ids = new StringBuilder("(");
      for (int i = 0; i < stockList.size(); i++) {
        ids.append(stockList.get(i).getId());
        if (i < stockList.size() - 1)
          ids.append(", ");
      }
      ids.append(")");
      list.add("cc.stock IN " + ids.toString());
    }
    return list;
  }

  public List<CarModel> filterCarsByCarMake(List<CarModel> cars, SearchCarModel searchModel) {
    if (searchModel.getCarMake() == null || searchModel.getCarMake().getId() <= 0)
      return cars;
    return filterCarsByCarMake(cars, searchModel.getCarMake());
  }

  public List<CarModel> filterCarsByCarMake(List<CarModel> cars, CarMakeModel carMake) {
    if (cars == null || cars.isEmpty() || carMake == null)
      return cars;
    List<CarModel> result = new ArrayList<>();
    for (CarModel car : cars) {
      CarTypeModel carType = car.getCarType();
      if (carType != null && carType.getCarMake() != null && carType.getCarMake().equals(carMake))
        result.add(car);
    }
    return result;
  }
}
