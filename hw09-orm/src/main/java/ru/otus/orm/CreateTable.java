package ru.otus.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateTable {
  
  private final String sql;

  private final String tableName;
  
  private static Logger logger = LoggerFactory.getLogger(CreateTable.class);

  public CreateTable(String sql, String tableName) {
    this.sql = sql;
    this.tableName = tableName;
  }

  public void create(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
       PreparedStatement pst = connection.prepareStatement(sql)) {
      pst.executeUpdate();
    }
    logger.info("Table created. Name: " + " \"" + tableName.toLowerCase() + "\"");

  }
}
