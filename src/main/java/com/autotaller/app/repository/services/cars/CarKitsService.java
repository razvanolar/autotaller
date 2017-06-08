package com.autotaller.app.repository.services.cars;

import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.model.CarSubkitModel;
import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 29.04.2017
 */
public class CarKitsService extends GenericService {

  private String SELECT_ALL_CAR_SUBKITS = "SELECT cs.id, cs.name, cs.gasoline, cs.diesel, cs.gpl, cs.electric, k.id, k.name, cc.id, cc.name " +
          "FROM car_subkits cs INNER JOIN car_kits k ON cs.car_kit_id = k.id INNER JOIN car_kit_categories cc ON k.category = cc.id";

  public CarKitsService(JDBCUtil jdbcUtil) {
    super(jdbcUtil);
  }

  public List<CarKitCategoryModel> getCarKitCategories() throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = "SELECT id, name FROM car_kit_categories";
      statement = connection.prepareStatement(query);
      rs = statement.executeQuery();
      List<CarKitCategoryModel> result = new ArrayList<>();
      while (rs.next()) {
        result.add(new CarKitCategoryModel(rs.getInt(1), rs.getString(2)));
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

  public List<CarKitModel> getCarKits() throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = "SELECT k.id, k.name, kc.id, kc.name FROM car_kits k INNER JOIN car_kit_categories kc ON k.category = kc.id";
      statement = connection.prepareStatement(query);
      rs = statement.executeQuery();
      List<CarKitModel> result = new ArrayList<>();
      while (rs.next()) {
        result.add(new CarKitModel(
                rs.getInt(1),
                rs.getString(2),
                new CarKitCategoryModel(rs.getInt(3), rs.getString(4))
                )
        );
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

  public List<CarSubkitModel> getCarSubkits() throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      statement = connection.prepareStatement(SELECT_ALL_CAR_SUBKITS);
      return getCarSubkitsFromStatement(statement);
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, rs);
    }
  }

  public CarSubkitModel getCarSubkitById(int subkitId) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = SELECT_ALL_CAR_SUBKITS + " WHERE cs.id = ?";
      statement = connection.prepareStatement(query);
      statement.setInt(1, subkitId);
      List<CarSubkitModel> result = getCarSubkitsFromStatement(statement);
      return result != null && !result.isEmpty() ? result.get(0) : null;
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  public void addCarKit(CarKitModel carKit) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String sql = "INSERT INTO car_kits (name, category) VALUES (?, ?)";
      statement = connection.prepareStatement(sql);
      statement.setString(1, carKit.getName());
      statement.setInt(2, carKit.getKitCategory().getId());
      statement.executeUpdate();
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  public void addCarSubkit(CarSubkitModel carSubkit) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String sql = "INSERT INTO car_subkits (name, car_kit_id, gasoline, diesel, gpl, electric) VALUES (?, ?, ?, ?, ?, ?)";
      statement = connection.prepareStatement(sql);
      statement.setString(1, carSubkit.getName());
      statement.setInt(2, carSubkit.getCarKit().getId());
      statement.setInt(3, carSubkit.isUsedForGasoline() ? 1 : 0);
      statement.setInt(4, carSubkit.isUsedForDiesel() ? 1 : 0);
      statement.setInt(5, carSubkit.isUsedForGPL() ? 1 : 0);
      statement.setInt(6, carSubkit.isUsedForElectric() ? 1 : 0);
      statement.executeUpdate();
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  private List<CarSubkitModel> getCarSubkitsFromStatement(PreparedStatement statement) throws Exception {
    ResultSet rs = null;
    try {
      rs = statement.executeQuery();
      List<CarSubkitModel> result = new ArrayList<>();
      while (rs.next()) {
        result.add(new CarSubkitModel(
                rs.getInt(1),
                rs.getString(2),
                new CarKitModel(rs.getInt(7), rs.getString(8), new CarKitCategoryModel(rs.getInt(9), rs.getString(10))),
                rs.getBoolean(3),
                rs.getBoolean(4),
                rs.getBoolean(5),
                rs.getBoolean(6)
        ));
      }
      return result;
    } finally {
      jdbcUtil.closeResultSet(rs);
    }
  }
}
