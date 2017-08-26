package com.autotaller.app.repository.services.cars;

import com.autotaller.app.model.CarBodyTypeModel;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;
import com.autotaller.app.utils.CarWheelSideType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 02.05.2017
 */
public class CarService extends GenericService {

  private String ALL_CARS_QUERY = "SELECT c.id, c.model_id, c.name, cbt.id, cbt.name, c.produced_from, c.produced_to, " +
          "c.production_year, c.km, c.kw, c.cilindrical_capacity, c.cilinders, c.description, cf.id, cf.name, " +
          "c.color_code, c.price, c.wheel_side FROM cars c INNER JOIN car_fuels cf ON c.fuel_id = cf.id " +
          "INNER JOIN car_body_types cbt ON c.body_type_id = cbt.id";

  private String DELETE_CAR_BY_ID_SQL = "DELETE FROM cars WHERE id = ?";

  private String DELETE_CAR_BY_CAR_MAKE_ID_SQL = "DELETE FROM cars WHERE model_id = ?";

  private String DELETE_ENGINES_FOR_CAR_ID_SQL = "DELETE FROM car_engines WHERE car_id = ?";

  private String CARS_BY_CAR_TYPE_QUERY = ALL_CARS_QUERY + " WHERE c.model_id = ?";

  private String CAR_BY_ID_QUERY = ALL_CARS_QUERY + " WHERE c.id = ?";

  public CarService(JDBCUtil jdbcUtil) {
    super(jdbcUtil);
  }

  public List<CarModel> getCars(SystemModelsDTO systemModelsDTO) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      // Retrieving all the cars without engine and car type information; car type info will be retrieved from systemModelsDTO
      //  and engines info will be retrieved by separate calls for each car
      statement = connection.prepareStatement(ALL_CARS_QUERY);
      return getCarsFromStatement(connection, statement, systemModelsDTO);
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  public List<CarModel> getCarsByTypeId(int carTypeId, SystemModelsDTO systemModelsDTO) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      statement = connection.prepareStatement(CARS_BY_CAR_TYPE_QUERY);
      statement.setInt(1, carTypeId);
      return getCarsFromStatement(connection, statement, systemModelsDTO);
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  private List<Integer> getCarIdsByTypeId(Connection connection, int carTypeId) throws Exception {
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      statement = connection.prepareStatement(CARS_BY_CAR_TYPE_QUERY);
      statement.setInt(1, carTypeId);
      rs = statement.executeQuery();
      List<Integer> result = new ArrayList<>();
      while (rs.next()) {
        result.add(rs.getInt(1));
      }
      return result;
    } finally {
      jdbcUtil.close(null, statement, rs);
    }
  }

  public CarModel getCarById(int carId, SystemModelsDTO systemModelsDTO) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      statement = connection.prepareStatement(CAR_BY_ID_QUERY);
      statement.setInt(1, carId);
      List<CarModel> carsList = getCarsFromStatement(connection, statement, systemModelsDTO);
      return !carsList.isEmpty() ? carsList.get(0) : null;
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  private List<CarModel> getCarsFromStatement(Connection connection, PreparedStatement statement, SystemModelsDTO systemModelsDTO) throws Exception {
    ResultSet rs = null;
    try {
      rs = statement.executeQuery();
      List<CarModel> result = new ArrayList<>();
      while (rs.next()) {
        int carId = rs.getInt(1);
        List<String> engines = getCarEnginesList(carId, connection);
        Date fromDate = rs.getDate(6);
        Date toDate = rs.getDate(7);
        Date productionDate = rs.getDate(8);
        result.add(new CarModel(carId, systemModelsDTO.getCarTypeModelById(rs.getInt(2)),
                rs.getString(3), new CarBodyTypeModel(rs.getInt(4), rs.getString(5)),
                fromDate != null ? fromDate.toLocalDate() : null, toDate != null ? toDate.toLocalDate() : null,
                productionDate != null ? productionDate.toLocalDate() : null, rs.getInt(9),
                rs.getInt(10), rs.getInt(11), rs.getInt(12), engines,
                systemModelsDTO.getFuelById(rs.getInt(14)), rs.getString(16),
                rs.getInt(17), CarWheelSideType.fromString(rs.getString(18)),
                rs.getString(13)));
      }
      return result;
    } finally {
      jdbcUtil.closeResultSet(rs);
    }
  }

  /**
   * Register the specified car alongside with the engines list.
   * @param car car model
   * @return the id of the registered car
   * @throws Exception in case some db operations fail
   */
  public int addCar(CarModel car) throws Exception {
    Connection connection = null;
    PreparedStatement addCarStatement = null;
    PreparedStatement carIdStatement = null;
    PreparedStatement addEnginesStatement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      connection.setAutoCommit(false);
      String addCarSql = "INSERT INTO cars (model_id, name, body_type_id, produced_from, produced_to, production_year, " +
              "km, kw, cilindrical_capacity, cilinders, fuel_id, color_code, price, wheel_side, description) " +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      addCarStatement = connection.prepareStatement(addCarSql);
      addCarStatement.setInt(1, car.getCarType().getId());
      addCarStatement.setString(2, car.getName());
      addCarStatement.setInt(3, car.getBodyType().getId());
      setStatementDate(4, car.getFrom(), addCarStatement);
      setStatementDate(5, car.getTo(), addCarStatement);
      setStatementDate(6, car.getProductionYear(), addCarStatement);
      addCarStatement.setInt(7, car.getKm());
      addCarStatement.setInt(8, car.getKw());
      addCarStatement.setInt(9, car.getCapacity());
      addCarStatement.setInt(10, car.getCilinders());
      addCarStatement.setInt(11, car.getFuel().getId());
      addCarStatement.setString(12, car.getColorCode());
      addCarStatement.setInt(13, car.getPrice());
      addCarStatement.setString(14, car.getWheelSide().getValue());
      if (car.getDescription() != null) {
        addCarStatement.setString(15, car.getDescription());
      } else {
        addCarStatement.setNull(15, Types.VARCHAR);
      }

      addCarStatement.executeUpdate();
      carIdStatement = connection.prepareStatement("SELECT max(id) FROM cars");
      rs = carIdStatement.executeQuery();
      if (!rs.next()) {
        connection.rollback();
        throw new Exception("Unable to determine car id");
      }
      int carId = rs.getInt(1);

      List<String> engines = car.getEnginesList();
      if (engines != null && !engines.isEmpty()) {
        String sql = "INSERT INTO car_engines (car_id, engine_name) VALUES (?, ?)";
        addEnginesStatement = connection.prepareStatement(sql);
        addEnginesStatement.setInt(1, carId);
        for (String engine : engines) {
          addEnginesStatement.setString(2, engine);
          addEnginesStatement.executeUpdate();
        }
      }

      connection.commit();
      return carId;
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      if (connection != null)
        connection.rollback();
      throw e;
    } finally {
      jdbcUtil.closeConnection(connection);
      jdbcUtil.closePrepareStatement(addCarStatement);
      jdbcUtil.closePrepareStatement(carIdStatement);
      jdbcUtil.closePrepareStatement(addEnginesStatement);
      jdbcUtil.closeResultSet(rs);
    }
  }

  public void deleteCar(CarModel car) throws Exception {
    Connection connection = null;
    try {
      connection = jdbcUtil.getNewConnection();
      connection.setAutoCommit(false);

      deleteCarById(connection, car.getId());

      connection.commit();
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      if (connection != null)
        connection.rollback();
      throw e;
    } finally {
      jdbcUtil.closeConnection(connection);
    }
  }

  public void deleteCarById(Connection connection, int carId) throws Exception {
    PreparedStatement deleteEnginesStatement = null;
    PreparedStatement deleteCarsStatement = null;
    try {
      //TODO delete also the car components associated whit this car

      deleteEnginesStatement = connection.prepareStatement(DELETE_ENGINES_FOR_CAR_ID_SQL);
      deleteEnginesStatement.setInt(1, carId);
      deleteEnginesStatement.executeUpdate();

      deleteCarsStatement = connection.prepareStatement(DELETE_CAR_BY_ID_SQL);
      deleteCarsStatement.setInt(1, carId);
      deleteCarsStatement.executeUpdate();
    } finally {
      jdbcUtil.closePrepareStatement(deleteEnginesStatement);
      jdbcUtil.closePrepareStatement(deleteCarsStatement);
    }
  }

  public void deleteCarsByTypeId(Connection connection, int carTypeId) throws Exception {
    List<Integer> carIds = getCarIdsByTypeId(connection, carTypeId);
    for (Integer id : carIds) {
      deleteCarById(connection, id);
    }
  }

  private List<String> getCarEnginesList(int carId, Connection connection) throws Exception {
    Statement statement = null;
    ResultSet rs = null;
    try {
      statement = connection.createStatement();
      rs = statement.executeQuery("SELECT engine_name FROM car_engines WHERE car_id = " + carId);
      List<String> result = new ArrayList<>();
      while (rs.next()) {
        result.add(rs.getString(1));
      }
      return result;
    } finally {
      jdbcUtil.closeResultSet(rs);
      jdbcUtil.closeStatement(statement);
    }
  }

  private void setStatementDate(int index, LocalDate date, PreparedStatement statement) throws Exception {
    if (date != null) {
      statement.setDate(index, Date.valueOf(date));
    } else {
      statement.setNull(index, Types.DATE);
    }
  }
}
