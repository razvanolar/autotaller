package com.autotaller.app.repository.utils;

import java.sql.*;

/**
 * Created by razvanolar on 11.04.2017
 */
public class JDBCUtil {

  private String host = "jdbc:mysql://localhost:3306/autotaller?characterEncoding=utf8&useUnicode=true&sessionVariables=storage_engine%3DInnoDB&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
  private String user = "root";
  private String password = "root";

  private int retry = 5;

  public JDBCUtil() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
  }

  /**
   * Testing DB connection.
   * Called when the application starts
   */
  public void testConnection() throws SQLException {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      connection = getNewConnection();
      String query = "SHOW TABLES";
      statement = connection.prepareStatement(query);
      resultSet = statement.executeQuery();
    } catch (SQLException e) {
      //TODO Handle test connection error
      e.printStackTrace();
      throw e;
    } finally {
      close(connection, statement, resultSet);
    }
  }

  public Connection getNewConnection() throws SQLException {
    return DriverManager.getConnection(host, user, password);
  }

  public void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
    closeResultSet(resultSet);
    closePrepareStatement(statement);
    closeConnection(connection);
  }

  public void closeConnection(Connection connection) {
    closeConnection(connection, retry);
  }

  private void closeConnection(Connection connection, int attemptsLeft) {
    if (connection == null)
      return;
    if (attemptsLeft <= 0) {
      //TODO Handle exception when unable to close connection
      return;
    }

    try {
      if (!connection.isClosed())
        connection.close();
    } catch (SQLException e) {
      //TODO Handle exception when trying to close connection
      attemptsLeft--;
      System.out.println("Unable to close connection. Attempts left: " + attemptsLeft);
      closeConnection(connection, attemptsLeft);
    }
  }

  public void closePrepareStatement(PreparedStatement statement) {
    closePrepareStatement(statement, retry);
  }

  private void closePrepareStatement(PreparedStatement statement, int attemptsLeft) {
    if (statement == null)
      return;
    if (attemptsLeft <= 0) {
      //TODO Handle exception when unable to close the prepare statement
      return;
    }
    try {
      if (!statement.isClosed())
        statement.close();
    } catch (SQLException e) {
      //TODO Handle exception when trying to close the prepare statement
      attemptsLeft--;
      System.out.println("Unable to close prepare statement. Attempts left: " + attemptsLeft);
      closePrepareStatement(statement, attemptsLeft);
    }
  }

  public void closeResultSet(ResultSet resultSet) {
    closeResultSet(resultSet, retry);
  }

  private void closeResultSet(ResultSet resultSet, int attemptsLeft) {
    if (resultSet == null)
      return;
    if (attemptsLeft <= 0) {
      //TODO Handle exception when unable to close the result set
      return;
    }
    try {
      if (!resultSet.isClosed())
        resultSet.close();
    } catch (SQLException e) {
      //TODO Handle exception when trying to close the result set
      attemptsLeft--;
      System.out.println("Unable to close result set. Attempts left: " + attemptsLeft);
      closeResultSet(resultSet, attemptsLeft);
    }
  }
}
