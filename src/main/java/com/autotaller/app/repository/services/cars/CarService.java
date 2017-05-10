package com.autotaller.app.repository.services.cars;

import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 02.05.2017
 */
public class CarService extends GenericService {

  public CarService(JDBCUtil jdbcUtil) {
    super(jdbcUtil);
  }

  public List<CarModel> getCars(SystemModelsDTO systemModelsDTO) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      // Retrieving all the cars without engine and car type information; car type info will be retrieved from systemModelsDTO
      //  and engines info will be retrieved by separate calls for each car
      String query = "SELECT c.id, c.model_id, c.name, c.produced_from, c.produced_to, c.kw, c.cilindrical_capacity, " +
              "c.cilinders, c.description, cf.id, cf.name FROM cars c INNER JOIN car_fuels cf ON c.fuel_id = cf.id";
      statement = connection.prepareStatement(query);
      rs = statement.executeQuery();

      List<CarModel> result = new ArrayList<>();
      while (rs.next()) {
        int carId = rs.getInt(1);
        List<String> engines = getCarEnginesList(carId, connection);
        result.add(new CarModel(carId, systemModelsDTO.getCarTypeModelById(rs.getInt(2)),
                rs.getString(3), rs.getDate(4).toLocalDate(),
                rs.getDate(5).toLocalDate(), rs.getInt(6), rs.getInt(7),
                rs.getInt(8), engines, systemModelsDTO.getFuelById(rs.getInt(10)),
                rs.getString(9)));
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

  public void addCar(CarModel car, List<CarComponentModel> components) throws Exception {
    Connection connection = null;
    PreparedStatement addCarStatement = null;
    PreparedStatement carIdStatement = null;
    PreparedStatement addEnginesStatement = null;
    PreparedStatement addComponentsStatement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      connection.setAutoCommit(false);
      String addCarSql = "INSERT INTO cars (model_id, name, produced_from, produced_to, kw, cilindrical_capacity, cilinders, fuel_id, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
      addCarStatement = connection.prepareStatement(addCarSql);
      addCarStatement.setInt(1, car.getCarType().getId());
      addCarStatement.setString(2, car.getName());
      addCarStatement.setDate(3, Date.valueOf(car.getFrom()));
      addCarStatement.setDate(4, Date.valueOf(car.getTo()));
      addCarStatement.setInt(5, car.getKw());
      addCarStatement.setInt(6, car.getCapacity());
      addCarStatement.setInt(7, car.getCilinders());
      addCarStatement.setInt(8, car.getFuel().getId());
      if (car.getDescription() != null) {
        addCarStatement.setString(9, car.getDescription());
      } else {
        addCarStatement.setNull(9, Types.VARCHAR);
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

      if (components != null && !components.isEmpty()) {
        String componentSql = "INSERT INTO car_components (car_id, subkit_id, component_name, code, stock, description) VALUES (?, ?, ?, ?, ?, ?)";
        addComponentsStatement = connection.prepareStatement(componentSql);
        addComponentsStatement.setInt(1, carId);
        for (CarComponentModel component : components) {
          addComponentsStatement.setInt(2, component.getCarSubkitId());
          addComponentsStatement.setString(3, component.getName());
          addComponentsStatement.setString(4, component.getCode());
          addComponentsStatement.setString(5, component.getStock());
          addComponentsStatement.setString(6, component.getDescription());
          addComponentsStatement.executeUpdate();
        }
      }

      connection.commit();
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
      jdbcUtil.closePrepareStatement(addComponentsStatement);
      jdbcUtil.closeResultSet(rs);
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
}
