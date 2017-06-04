package com.autotaller.app.repository.services;

import com.autotaller.app.model.UserModel;
import com.autotaller.app.repository.utils.JDBCUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by razvanolar on 11.04.2017
 */
public class UserService extends GenericService {

  private MessageDigest md5;

  public UserService(JDBCUtil jdbcUtil) throws NoSuchAlgorithmException {
    super(jdbcUtil);
    md5 = MessageDigest.getInstance("MD5");
  }

  public UserModel getUserIdByCredentials(String username, String password) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String sql = "SELECT id, name, username FROM users WHERE username = ? AND password = ?";
      statement = connection.prepareStatement(sql);
      statement.setString(1, username);
      statement.setString(2, encriptPassword(password));
      rs = statement.executeQuery();
      return rs.next() ? new UserModel(rs.getInt(1), rs.getString(2), rs.getString(3)) : null;
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, rs);
    }
  }

  public boolean checkUserPassword(int userId, String password) throws Exception {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = jdbcUtil.getNewConnection();
      String query = "SELECT * FROM users WHERE id = ? and password = ?";
      statement = connection.prepareStatement(query);
      statement.setInt(1, userId);
      statement.setString(2, encriptPassword(password));
      rs = statement.executeQuery();
      return rs.next();
    } catch (Exception e) {
      //TODO handle exception
      e.printStackTrace();
      throw e;
    } finally {
      jdbcUtil.close(connection, statement, rs);
    }
  }

  private String encriptPassword(String password) {
    byte[] bytes = md5.digest(password.getBytes());
    String value = "";
    for (byte b : bytes) {
      if ((0xff & b) < 0x10)
        value += "0" + Integer.toHexString(0xff & b);
      else
        value += Integer.toHexString(0xff & b);
    }
    return value;
  }
}
