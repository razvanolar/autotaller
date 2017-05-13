package com.autotaller.app.repository.services.cars;

import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;

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

  public List<CarComponentModel> getComponentsByCarId(int carId) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = "SELECT id, car_id, subkit_id, component_name, code, stock, description FROM car_components WHERE car_id = ?";
      statement = connection.prepareStatement(query);
      statement.setInt(1, carId);
      rs = statement.executeQuery();
      List<CarComponentModel> result = new ArrayList<>();
      while (rs.next()) {
        result.add(new CarComponentModel(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                rs.getString(4), rs.getString(5), rs.getString(6),
                rs.getString(7)));
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
