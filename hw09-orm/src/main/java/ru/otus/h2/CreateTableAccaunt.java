package ru.otus.h2;

import ru.otus.orm.CreateTable;

import javax.sql.DataSource;
import java.sql.SQLException;

public class CreateTableAccaunt extends CreateTable {

  public CreateTableAccaunt() {
    super("CREATE TABLE accaunt (no int NOT NULL auto_increment, TYPE varchar(23), REST number)", "accaunt");
  }

  @Override
  public void create(DataSource dataSource) throws SQLException {
     super.create(dataSource);
  }
}