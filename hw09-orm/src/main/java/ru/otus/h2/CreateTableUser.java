package ru.otus.h2;

import ru.otus.orm.CreateTable;

import javax.sql.DataSource;
import java.sql.SQLException;

public class CreateTableUser extends CreateTable {

  public CreateTableUser() {
    super("CREATE TABLE user (id int NOT NULL auto_increment, NAME varchar(23), AGE int(3))", "User");
  }

  @Override
  public void create(DataSource dataSource) throws SQLException {
    super.create(dataSource);
  }
}
