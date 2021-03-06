package ru.otus.h2;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class DataSourceH2 implements DataSource {
  private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
  private static final String USER = "admin";
  private static final String PASS = "admin";

  @Override
  public Connection getConnection() throws SQLException {
    Connection connection = DriverManager.getConnection(URL, USER, PASS);
    connection.setAutoCommit(false);
    return connection;
  }

  @Override
  public Connection getConnection(String username, String password) {
    return null;
  }

  @Override
  public PrintWriter getLogWriter() {
    return null;
  }

  @Override
  public void setLogWriter(PrintWriter out) {
    throw new UnsupportedOperationException();

  }

  @Override
  public int getLoginTimeout() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setLoginTimeout(int seconds) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Logger getParentLogger() {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T unwrap(Class<T> iface) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) {
    throw new UnsupportedOperationException();
  }
}
