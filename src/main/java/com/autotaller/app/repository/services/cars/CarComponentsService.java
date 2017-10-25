package com.autotaller.app.repository.services.cars;

import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.model.SearchComponentModel;
import com.autotaller.app.model.notifications.SimpleSellModel;
import com.autotaller.app.model.utils.PreSellComponentModel;
import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;
import com.autotaller.app.utils.SellModelStatus;
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

  private String SELECT_ALL_COMPONENTS = "SELECT cc.id, cc.car_id, cs.id, cs.name, cc.component_name, cc.code, cc.description, " +
          "cc.initial_pieces_no, cc.sold_pieces_no, cc.usage_state, cc.price, cc.stock FROM car_components cc INNER JOIN car_subkits cs ON cc.subkit_id = cs.id";

  private String SELECT_ALL_SIMPLE_SELL_MODELS = "SELECT ccs.id, ccs.component_id, cc.component_name, ccs.sold_pieces, cc.price, ccs.price, ccs.sold_date, ccs.user_id, u.username, ccs.status " +
          "FROM car_component_sales ccs INNER JOIN car_components cc ON ccs.component_id = cc.id " +
          "INNER JOIN users u ON ccs.user_id = u.id";

  public CarComponentsService(JDBCUtil jdbcUtil) {
    super(jdbcUtil);
  }

  public int getCarComponentsCount() throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = "SELECT count(*) FROM car_components";
      statement = connection.prepareStatement(query);
      rs = statement.executeQuery();
      return rs.next() ? rs.getInt(1) : 0;
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, rs);
    }
  }

  /**
   * Retrieve car components in the specified bound. If @offset is -1 then all DB entries will be selected
   * @param limit  - number of entries to be returned
   * @param offset - select entries starting from the specified offset
   * @return a list of car components
   * @throws Exception if their will be some unexpected sql error
   */
  public List<CarComponentModel> getCarComponents(int limit, int offset) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = SELECT_ALL_COMPONENTS + (offset >= 0 ? " LIMIT " + limit + " OFFSET " + offset : "");
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

  public CarComponentModel getCarComponentById(int componentId) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = SELECT_ALL_COMPONENTS + " WHERE cc.id = ?";
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

  public List<CarComponentModel> getCarComponentBySearchModel(SearchComponentModel searchModel) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String conditions = concatenateSearchConditions(computeSearchConditions(searchModel));
      String query = StringValidator.isNullOrEmpty(conditions) ? SELECT_ALL_COMPONENTS : SELECT_ALL_COMPONENTS + " WHERE " + conditions;
      System.out.println(query);
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

  public List<CarComponentModel> getComponentsByCarId(int carId) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = SELECT_ALL_COMPONENTS + " WHERE cc.car_id = ?";
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

  /**
   *  If @carId is 0 then we will select the components only by @kitId
   */
  public List<CarComponentModel> getComponentsByCarAndKitId(int carId, int kitId) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = SELECT_ALL_COMPONENTS + " INNER JOIN car_kits ck ON cs.car_kit_id = ck.id WHERE ck.id = ?";
      if (carId > 0) {
        query += " car_id = ?";
      }
      statement = connection.prepareStatement(query);
      statement.setInt(1, kitId);
      if (carId > 0) {
        statement.setInt(2, carId);
      }
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
      CarComponentModel component = getCarComponentById(componentId);

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

  public List<SimpleSellModel> getSimpleSellModels(SellModelStatus status) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      if (status != SellModelStatus.ALL) {
        statement = connection.prepareStatement(SELECT_ALL_SIMPLE_SELL_MODELS + " WHERE ccs.status = ?");
        statement.setInt(1, status.getValue());
      } else {
        statement = connection.prepareStatement(SELECT_ALL_SIMPLE_SELL_MODELS);
      }
      return getSimpleSellModelsFromStatement(statement);
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  public void confirmSellModel(SimpleSellModel sellModel) throws Exception {
    Connection connection = null;
    PreparedStatement sellStatement = null;
    PreparedStatement componentStatement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      connection.setAutoCommit(false);
      sellStatement = connection.prepareStatement("UPDATE car_component_sales SET status = 1 WHERE id = ?");
      sellStatement.setInt(1, sellModel.getId());
      sellStatement.executeUpdate();
      componentStatement = connection.prepareStatement("UPDATE car_components SET sold_pieces_no = sold_pieces_no + ? WHERE id = ?");
      componentStatement.setInt(1, sellModel.getSoldPieces());
      componentStatement.setInt(2, sellModel.getComponentId());
      componentStatement.executeUpdate();
      connection.commit();
    } catch (Exception e) {
      if (connection != null)
        connection.rollback();
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.closeConnection(connection);
      jdbcUtil.closeStatement(sellStatement);
      jdbcUtil.closeStatement(componentStatement);
    }
  }

  public void cancelSellModel(int sellNotificationId) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      statement = connection.prepareStatement("DELETE FROM car_component_sales WHERE id = ?");
      statement.setInt(1, sellNotificationId);
      statement.executeUpdate();
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  public void editComponent(CarComponentModel carComponent) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String sql = "UPDATE car_components SET component_name = ?, code = ?, description = ?, stock = ?, initial_pieces_no = ?, usage_state = ?, price = ? WHERE id = ?";
      statement = connection.prepareStatement(sql);
      statement.setString(1, carComponent.getName());
      statement.setString(2, carComponent.getCode());
      statement.setString(3, carComponent.getDescription());
      statement.setInt(4, carComponent.getStock().getId());
      statement.setInt(5, carComponent.getInitialPieces());
      statement.setInt(6, carComponent.getUsageState().getId());
      statement.setInt(7, carComponent.getPrice());
      statement.setInt(8, carComponent.getId());
      statement.executeUpdate();
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, null);
    }
  }

  public void deleteComponent(CarComponentModel carComponent) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String sql = "DELETE FROM car_components WHERE id = ?";
      statement = connection.prepareStatement(sql);
      statement.setInt(1, carComponent.getId());
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
                componentResultSet.getString(7),
                componentResultSet.getInt(8),
                componentResultSet.getInt(9),
                UsageStateType.fromId(componentResultSet.getInt(10)),
                componentResultSet.getInt(11),
                StockType.fromId(componentResultSet.getInt(12))
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

  private List<SimpleSellModel> getSimpleSellModelsFromStatement(PreparedStatement statement) throws Exception {
    ResultSet rs = null;
    try {
      rs = statement.executeQuery();
      List<SimpleSellModel> result = new ArrayList<>();
      while (rs.next()) {
        result.add(new SimpleSellModel(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getInt(5),
                rs.getInt(6),
                rs.getDate(7).toLocalDate(),
                rs.getInt(8),
                rs.getString(9),
                rs.getInt(10)
        ));
      }
      return result;
    } finally {
      jdbcUtil.closeResultSet(rs);
    }
  }

  private String concatenateSearchConditions(List<String> conditions) {
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

  private List<String> computeSearchConditions(SearchComponentModel searchModel) {
    if (searchModel == null)
      return new ArrayList<>();
    List<String> list = new ArrayList<>();
    if (!StringValidator.isNullOrEmpty(searchModel.getName()))
      list.add("cc.component_name LIKE '%" + searchModel.getName() + "%'");
    if (!StringValidator.isNullOrEmpty(searchModel.getCode()))
      list.add("cc.code LIKE '%" + searchModel.getCode() + "%'");
    if (searchModel.getMinPrice() > 0)
      list.add("cc.price > " + searchModel.getMinPrice());
    if (searchModel.getMaxPrice() > 0)
      list.add("cc.price < " + searchModel.getMaxPrice());

    List<UsageStateType> usageList = searchModel.getUsageList();
    if (usageList != null && !usageList.isEmpty() && usageList.size() != UsageStateType.values().length) {
      StringBuilder ids = new StringBuilder("(");
      for (int i = 0; i < usageList.size(); i++) {
        ids.append(String.valueOf(usageList.get(i).getId()));
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
        ids.append(String.valueOf(stockList.get(i).getId()));
        if (i < stockList.size() - 1)
          ids.append(", ");
      }
      ids.append(")");
      list.add("cc.stock IN " + ids.toString());
    }
    return list;
  }
}
