package com.autotaller.app.repository.services.cars;

import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;

import java.sql.*;
import java.util.List;

/**
 * Created by razvanolar on 02.05.2017
 */
public class CarService extends GenericService {

  public CarService(JDBCUtil jdbcUtil) {
    super(jdbcUtil);
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
}
