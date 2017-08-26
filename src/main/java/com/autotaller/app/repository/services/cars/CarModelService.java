package com.autotaller.app.repository.services.cars;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 28.04.2017
 */
public class CarModelService extends GenericService {

  private String GET_CAR_MODELS = "SELECT c1.id, c1.name, c1.production_from, c1.production_to, c2.id, c2.name FROM car_models c1 INNER JOIN car_makes c2 ON c1.make_id = c2.id";

  private String DELETE_CAR_TYPE_BY_ID_SQL = "DELETE FROM car_models WHERE id = ?";

  private CarService carService;

  public CarModelService(JDBCUtil jdbcUtil, CarService carService) {
    super(jdbcUtil);
    this.carService = carService;
  }

  public List<CarTypeModel> getCarModels() throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    PreparedStatement frameStatement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = GET_CAR_MODELS;
      statement = connection.prepareStatement(query);
      rs = statement.executeQuery();
      List<CarTypeModel> result = new ArrayList<>();
      frameStatement = connection.prepareStatement("SELECT frame_name FROM car_model_frames WHERE car_model_id = ?");
      while (rs.next()) {
        result.add(collect(rs, frameStatement));
      }
      return result;
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, rs);
      jdbcUtil.closePrepareStatement(frameStatement);
    }
  }

  public CarTypeModel getCarModelById(int carModelId) throws Exception {
    Connection connection = null;
    try {
      connection = jdbcUtil.getNewConnection();
      return getCarModelById(connection, carModelId);
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.closeConnection(connection);
    }
  }

  public CarTypeModel getCarModelById(Connection connection, int carModelId) throws Exception {
    Statement statement = null;
    PreparedStatement frameStatement = null;
    ResultSet rs = null;
    try {
      statement = connection.createStatement();
      frameStatement = connection.prepareStatement("SELECT frame_name FROM car_model_frames WHERE car_model_id = ?");
      rs = statement.executeQuery(GET_CAR_MODELS + " WHERE c1.id = " + carModelId);
      return rs.next() ? collect(rs, frameStatement) : null;
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.closeResultSet(rs);
      jdbcUtil.closePrepareStatement(frameStatement);
      jdbcUtil.closeStatement(statement);
    }
  }

  private List<Integer> getCarTypeIdsByMakeId(Connection connection, int carMakeId) throws Exception {
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      statement = connection.prepareStatement("SELECT id FROM car_models WHERE make_id = ?");
      statement.setInt(1, carMakeId);
      rs = statement.executeQuery();
      List<Integer> result = new ArrayList<>();
      while (rs.next()) {
        result.add(rs.getInt(1));
      }
      return result;
    } finally {
      jdbcUtil.close(null, statement, rs);
    }
  }

  public void addCarModel(CarTypeModel carModel) throws Exception {
    Connection connection = null;
    PreparedStatement carStatement = null;
    PreparedStatement idStatement = null;
    PreparedStatement frameStatement = null;
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
        throw new Exception("Unable to determine car model id");
      }
      int carModelId = rs.getInt(1);

      List<String> frameNames = carModel.getFrameNames();
      if (frameNames != null && !frameNames.isEmpty()) {
        sql = "INSERT INTO car_model_frames (car_model_id, frame_name) VALUES (?, ?)";
        frameStatement = connection.prepareStatement(sql);
        frameStatement.setInt(1, carModelId);
        for (String farame : frameNames) {
          frameStatement.setString(2, farame);
          frameStatement.executeUpdate();
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
      jdbcUtil.closePrepareStatement(frameStatement);
    }
  }

  public void deleteCarType(CarTypeModel carType) throws Exception {
    Connection connection = null;
    try {
      connection = jdbcUtil.getNewConnection();
      connection.setAutoCommit(false);

      deleteCarTypeById(connection, carType.getId());

      connection.commit();
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      if (connection != null)
        connection.rollback();
      throw e;
    } finally {
      jdbcUtil.closeConnection(connection);
    }
  }

  public void deleteCarTypeById(Connection connection, int carTypeId) throws Exception {
    PreparedStatement deleteCarTypeFramesStatement = null;
    PreparedStatement deleteCarTypeStatement = null;
    try {
      carService.deleteCarsByTypeId(connection, carTypeId);

      deleteCarTypeFramesStatement = connection.prepareStatement("DELETE FROM car_model_frames WHERE car_model_id = ?");
      deleteCarTypeFramesStatement.setInt(1, carTypeId);
      deleteCarTypeFramesStatement.executeUpdate();

      deleteCarTypeStatement = connection.prepareStatement(DELETE_CAR_TYPE_BY_ID_SQL);
      deleteCarTypeStatement.setInt(1, carTypeId);
      deleteCarTypeStatement.executeUpdate();
    } finally {
      jdbcUtil.closePrepareStatement(deleteCarTypeFramesStatement);
      jdbcUtil.closePrepareStatement(deleteCarTypeStatement);
    }
  }

  public void deleteCarTypesByMakeId(Connection connection, int carMakeId) throws Exception {
    List<Integer> carTypeIds = getCarTypeIdsByMakeId(connection, carMakeId);
    for (Integer id : carTypeIds) {
      deleteCarTypeById(connection, id);
    }
  }

  private CarTypeModel collect(ResultSet rs, PreparedStatement frameStatement) throws Exception {
    int id = rs.getInt(1);
    Date toDate = rs.getDate(4);
    return new CarTypeModel(
            id,
            new CarMakeModel(rs.getInt(5), rs.getString(6)),
            rs.getString(2),
            rs.getDate(3).toLocalDate(),
            toDate != null ? toDate.toLocalDate() : null,
            getFramesForCarModelId(id, frameStatement)
    );
  }

  private List<String> getFramesForCarModelId(int carModelId, PreparedStatement statement) throws Exception {
    ResultSet rs = null;
    try {
      statement.setInt(1, carModelId);
      rs = statement.executeQuery();
      List<String> frame = new ArrayList<>();
      while (rs.next()) {
        frame.add(rs.getString(1));
      }
      return frame;
    } finally {
      jdbcUtil.closeResultSet(rs);
    }
  }
}
