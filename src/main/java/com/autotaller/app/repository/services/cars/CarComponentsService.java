package com.autotaller.app.repository.services.cars;

import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;
import com.autotaller.app.utils.StringValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 13.05.2017
 */
public class CarComponentsService extends GenericService {

  public CarComponentsService(JDBCUtil jdbcUtil) {
    super(jdbcUtil);
  }

  public List<CarComponentModel> getCarComponents() throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = "SELECT id, car_id, subkit_id, component_name, code, stock, description, initial_pieces_no, sold_pieces_no FROM car_components";
      statement = connection.prepareStatement(query);
      return getCarComponentsFromStatement(statement);
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  public List<CarComponentModel> getComponentsByCarId(int carId) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = "SELECT id, car_id, subkit_id, component_name, code, stock, description, initial_pieces_no, sold_pieces_no FROM car_components WHERE car_id = ?";
      statement = connection.prepareStatement(query);
      statement.setInt(1, carId);
      return getCarComponentsFromStatement(statement);
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  public List<CarComponentModel> getComponentsByCarAndKitId(int carId, int kitId) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = "SELECT cc.id, cc.car_id, cc.subkit_id, cc.component_name, cc.code, cc.stock, cc.description, " +
              "cc.initial_pieces_no, cc.sold_pieces_no FROM car_components cc INNER JOIN car_subkits cs " +
              "ON cc.subkit_id = cs.id INNER JOIN car_kits ck ON cs.car_kit_id = ck.id WHERE car_id = ? AND ck.id = ?";
      statement = connection.prepareStatement(query);
      statement.setInt(1, carId);
      statement.setInt(2, kitId);
      return getCarComponentsFromStatement(statement);
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  public void addComponents(List<CarComponentModel> components) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      connection.setAutoCommit(false);
      String sql = "INSERT INTO car_components (car_id, subkit_id, component_name, code, stock, description, initial_pieces_no, sold_pieces_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      statement = connection.prepareStatement(sql);
      for (CarComponentModel component : components) {
        statement.setInt(1, component.getCarId() > 0 ? component.getCarId() : 0);
        statement.setInt(2, component.getCarSubkitId());
        statement.setString(3, component.getName());
        statement.setString(4, component.getCode());
        statement.setString(5, component.getStock());
        statement.setString(6, StringValidator.isNullOrEmpty(component.getDescription()) ? "" : component.getDescription());
        statement.setInt(7, component.getInitialPieces());
        statement.setInt(8, component.getSoldPieces());
        statement.executeUpdate();
      }
      connection.commit();
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      if (connection != null)
        connection.rollback();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  private List<CarComponentModel> getCarComponentsFromStatement(PreparedStatement statement) throws Exception {
    ResultSet rs = null;
    try {
      rs = statement.executeQuery();
      List<CarComponentModel> result = new ArrayList<>();
      while (rs.next()) {
        result.add(new CarComponentModel(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                rs.getString(4), rs.getString(5), rs.getString(6),
                rs.getString(7), rs.getInt(8), rs.getInt(9)));
      }
      return result;
    } finally {
      jdbcUtil.closeResultSet(rs);
    }
  }
}
