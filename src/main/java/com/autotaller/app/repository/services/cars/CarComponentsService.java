package com.autotaller.app.repository.services.cars;

import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.model.utils.PreSellComponentModel;
import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;
import com.autotaller.app.utils.StockType;
import com.autotaller.app.utils.StringValidator;
import com.autotaller.app.utils.UsageStateType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 13.05.2017
 */
public class CarComponentsService extends GenericService {

  private String SELECT_ALL = "SELECT cc.id, cc.car_id, cc.subkit_id, cc.component_name, cc.code, cc.description, " +
          "cc.initial_pieces_no, cc.sold_pieces_no, cc.usage_state, cc.price, cc.stock FROM car_components cc";

  public CarComponentsService(JDBCUtil jdbcUtil) {
    super(jdbcUtil);
  }

  public List<CarComponentModel> getCarComponents() throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = SELECT_ALL;
      statement = connection.prepareStatement(query);
      return getCarComponentsFromStatement(connection, statement);
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  public CarComponentModel getComponentById(int componentId) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = SELECT_ALL + " WHERE cc.id = ?";
      statement = connection.prepareStatement(query);
      statement.setInt(1, componentId);
      List<CarComponentModel> result = getCarComponentsFromStatement(connection, statement);
      return result != null && !result.isEmpty() ? result.get(0) : null;
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
      String query = SELECT_ALL + " WHERE cc.car_id = ?";
      statement = connection.prepareStatement(query);
      statement.setInt(1, carId);
      return getCarComponentsFromStatement(connection, statement);
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
      String query = SELECT_ALL + " INNER JOIN car_subkits cs " +
              "ON cc.subkit_id = cs.id INNER JOIN car_kits ck ON cs.car_kit_id = ck.id WHERE car_id = ? AND ck.id = ?";
      statement = connection.prepareStatement(query);
      statement.setInt(1, carId);
      statement.setInt(2, kitId);
      return getCarComponentsFromStatement(connection, statement);
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
      String sql = "INSERT INTO car_components (car_id, subkit_id, component_name, code, description, initial_pieces_no," +
              " sold_pieces_no, usage_state, price, stock) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      statement = connection.prepareStatement(sql);
      for (CarComponentModel component : components) {
        statement.setInt(1, component.getCarId() > 0 ? component.getCarId() : 0);
        statement.setInt(2, component.getCarSubkitId());
        statement.setString(3, component.getName());
        statement.setString(4, component.getCode());
        statement.setString(5, StringValidator.isNullOrEmpty(component.getDescription()) ? "" : component.getDescription());
        statement.setInt(6, component.getInitialPieces());
        statement.setInt(7, component.getSoldPieces());
        statement.setInt(8, component.getUsageState().getId());
        statement.setInt(9, component.getPrice());
        statement.setInt(10, component.getStock().getId());
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

  public void addPreSellComponent(PreSellComponentModel preSellModel, int userId) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      int componentId = preSellModel.getCarComponent().getId();
      CarComponentModel component = getComponentById(componentId);

      if (component == null)
        throw new Exception("Piesa nu a putut fi gasita. Id: " + componentId);
      if (component.getLeftPieces() < preSellModel.getSoldPieces())
        throw new Exception("Nu exista suficiente bucati disponibile pentru " + component.getName());

      connection = jdbcUtil.getNewConnection();
      String query = "INSERT INTO car_component_sales (component_id, sold_pieces, price, sold_date, user_id, status) VALUES (?, ?, ?, now(), ?, 0)";
      statement = connection.prepareStatement(query);
      statement.setInt(1, componentId);
      statement.setInt(2, preSellModel.getSoldPieces());
      statement.setInt(3, preSellModel.getPrice());
      statement.setInt(4, userId);
      statement.executeUpdate();
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  private List<CarComponentModel> getCarComponentsFromStatement(Connection connection, PreparedStatement statement) throws Exception {
    ResultSet componentResultSet = null;
    PreparedStatement sellStatement = null;
    try {
      componentResultSet = statement.executeQuery();
      List<CarComponentModel> result = new ArrayList<>();
      String query = "SELECT SUM(sold_pieces) FROM car_component_sales WHERE component_id = ? AND status = 0";
      sellStatement = connection.prepareStatement(query);
      while (componentResultSet.next()) {
        int componentId = componentResultSet.getInt(1);
        CarComponentModel component = new CarComponentModel(
                componentId,
                componentResultSet.getInt(2),
                componentResultSet.getInt(3),
                componentResultSet.getString(4),
                componentResultSet.getString(5),
                componentResultSet.getString(6),
                componentResultSet.getInt(7),
                componentResultSet.getInt(8),
                UsageStateType.fromId(componentResultSet.getInt(9)),
                componentResultSet.getInt(10),
                StockType.fromId(componentResultSet.getInt(11))
        );
        sellStatement.setInt(1, componentId);
        ResultSet resultSet = sellStatement.executeQuery();
        if (resultSet.next()) {
          component.setSoldPieces(component.getSoldPieces() + resultSet.getInt(1));
        }
        jdbcUtil.closeResultSet(resultSet);
        result.add(component);
      }
      return result;
    } finally {
      jdbcUtil.closeStatement(sellStatement);
      jdbcUtil.closeResultSet(componentResultSet);
    }
  }
}
