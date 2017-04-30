package com.autotaller.app.repository.services.cars;

import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.model.CarKitModel;
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
}
