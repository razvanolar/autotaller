package com.autotaller.app.repository.services.cars;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by razvanolar on 28.04.2017
 */
public class CarModelService extends GenericService {

  public CarModelService(JDBCUtil jdbcUtil) {
    super(jdbcUtil);
  }

  public List<CarTypeModel> getCarModels() throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    PreparedStatement engineStatement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = "SELECT c1.id, c1.name, c1.production_from, c1.production_to, c2.id, c2.name FROM car_models c1 INNER JOIN car_makes c2 ON c1.make_id = c2.id";
      statement = connection.prepareStatement(query);
      rs = statement.executeQuery();
      List<CarTypeModel> result = new ArrayList<>();
      Set<Integer> ids = new HashSet<>();
      engineStatement = connection.prepareStatement("SELECT engine_name FROM car_model_engines WHERE car_model_id = ?");
      while (rs.next()) {
        int id = rs.getInt(1);
        result.add(new CarTypeModel(
                  id,
                  new CarMakeModel(rs.getInt(5), rs.getString(6)),
                  rs.getString(2),
                  rs.getDate(3).toLocalDate(),
                  rs.getDate(4).toLocalDate(),
                  getEnginesForCarModelId(id, engineStatement)
                )
        );
        ids.add(id);
      }
      return result;
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, rs);
      jdbcUtil.closePrepareStatement(engineStatement);
    }
  }

  public void addCarModel(CarTypeModel carModel) throws Exception {
    Connection connection = null;
    PreparedStatement carStatement = null;
    PreparedStatement idStatement = null;
    PreparedStatement engineStatement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      connection.setAutoCommit(false);

      String sql = "INSERT INTO car_models (name, production_from, production_to, make_id) VALUES (?, ?, ?, ?)";
      carStatement = connection.prepareStatement(sql);
      carStatement.setString(1, carModel.getName());
      carStatement.setDate(2, Date.valueOf(carModel.getFrom()));
      if (carModel.getTo() != null) {
        carStatement.setDate(3, Date.valueOf(carModel.getTo()));
      } else {
        carStatement.setNull(3, Types.DATE);
      }

      carStatement.setInt(4, carModel.getCarMake().getId());
      carStatement.executeUpdate();

      sql = "SELECT max(id) FROM car_models";
      idStatement = connection.prepareStatement(sql);
      rs = idStatement.executeQuery();
      if (!rs.next()) {
        connection.rollback();
        return;
      }
      int carModelId = rs.getInt(1);

      List<String> engineNames = carModel.getEngineNames();
      if (engineNames != null && !engineNames.isEmpty()) {
        sql = "INSERT INTO car_model_engines (car_model_id, engine_name) VALUES (?, ?)";
        engineStatement = connection.prepareStatement(sql);
        engineStatement.setInt(1, carModelId);
        for (String engine : engineNames) {
          engineStatement.setString(2, engine);
          engineStatement.executeUpdate();
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
      jdbcUtil.close(connection, null, rs);
      jdbcUtil.closePrepareStatement(carStatement);
      jdbcUtil.closePrepareStatement(idStatement);
      jdbcUtil.closePrepareStatement(engineStatement);
    }
  }

  private List<String> getEnginesForCarModelId(int carModelId, PreparedStatement statement) throws Exception {
    ResultSet rs = null;
    try {
      statement.setInt(1, carModelId);
      rs = statement.executeQuery();
      List<String> engines = new ArrayList<>();
      while (rs.next()) {
        engines.add(rs.getString(1));
      }
      return engines;
    } finally {
      jdbcUtil.closeResultSet(rs);
    }
  }
}
