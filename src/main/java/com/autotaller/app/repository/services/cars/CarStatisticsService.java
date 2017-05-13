package com.autotaller.app.repository.services.cars;

import com.autotaller.app.components.utils.statistics.CarTypeStatisticsModel;
import com.autotaller.app.model.simple_models.SimpleCarTypeModel;
import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by razvanolar on 13.05.2017
 */
public class CarStatisticsService extends GenericService {

  public CarStatisticsService(JDBCUtil jdbcUtil) {
    super(jdbcUtil);
  }

  public CarTypeStatisticsModel getCarTypeStatistics() throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      statement = connection.createStatement();
      String query = "SELECT cm.id, cm.make_id, cm.name, count(c.id) FROM car_models cm INNER JOIN cars c ON cm.id = c.model_id GROUP BY cm.id, cm.make_id, cm.name";
      rs = statement.executeQuery(query);
      Map<Integer, Map<SimpleCarTypeModel, Integer>> map = new HashMap<>();
      while (rs.next()) {
        int carMakeId = rs.getInt(2);
        int registeredCars = rs.getInt(4);
        SimpleCarTypeModel simpleModel = new SimpleCarTypeModel(rs.getInt(1), carMakeId, rs.getString(3));
        Map<SimpleCarTypeModel, Integer> subMap = map.get(carMakeId);
        if (subMap == null) {
          subMap = new HashMap<>();
          subMap.put(simpleModel, registeredCars);
          map.put(carMakeId, subMap);
        } else {
          subMap.put(simpleModel, registeredCars);
        }
      }
      return new CarTypeStatisticsModel(map);
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, rs);
    }
  }
}
