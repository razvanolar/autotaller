package com.autotaller.app.repository.services.cars;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 18.04.2017
 */
public class CarMakesService extends GenericService {

  public CarMakesService(JDBCUtil jdbcUtil) {
    super(jdbcUtil);
  }

  public List<CarMakeModel> getAllCarMakes() throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String sql = "SELECT * FROM car_makes";
      statement = connection.prepareStatement(sql);
      rs = statement.executeQuery();
      List<CarMakeModel> result = new ArrayList<>();
      while (rs.next()) {
        result.add(new CarMakeModel(rs.getInt(1), rs.getString(2)));
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

  public List<CarMakeModel> getCarMakesByIds(List<Integer> ids) throws Exception {
    Connection connection = null;
    try {
      connection = jdbcUtil.getNewConnection();
      return getCarMakesByIds(ids, connection);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.closeConnection(connection);
    }
  }

  public List<CarMakeModel> getCarMakesByIds(List<Integer> ids, Connection connection) throws Exception {
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      String query = "SELECT id, name FROM car_makes WHERE id  IN (" + jdbcUtil.getInnerClause(ids) + ")";
      statement = connection.prepareStatement(query);
      rs = statement.executeQuery();
      List<CarMakeModel> result = new ArrayList<>();
      while (rs.next()) {
        result.add(new CarMakeModel(rs.getInt(1), rs.getString(2)));
      }
      return result;
    } finally {
      jdbcUtil.closePrepareStatement(statement);
      jdbcUtil.closeResultSet(rs);
    }
  }

  public void addCarMake(String name) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String insertStatement = "INSERT INTO car_makes (`name`) VALUES (?)";
      statement = connection.prepareStatement(insertStatement);
      statement.setString(1, name);
      statement.executeUpdate();
    } catch (Exception e) {
      //TODO handle exeption
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }
}
