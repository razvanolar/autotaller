package com.autotaller.app.repository.services.cars;

import com.autotaller.app.model.FuelModel;
import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 06.05.2017
 */
public class CarUtilsService extends GenericService {

  public CarUtilsService(JDBCUtil jdbcUtil) {
    super(jdbcUtil);
  }

  public List<FuelModel> getCarFuels() throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = "SELECT id, name FROM car_fuels";
      statement = connection.prepareStatement(query);
      rs = statement.executeQuery();
      List<FuelModel> result = new ArrayList<>();
      while (rs.next()) {
        result.add(new FuelModel(rs.getInt(1), rs.getString(2)));
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
}
