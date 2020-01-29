package ru.otus.orm.sql.generator;

import ru.otus.orm.Id;

import java.lang.reflect.*;

public class SqlQueryGenerator {

  private StringBuilder columnGenerator(Object object) {
    var sb = new StringBuilder();

    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (field.isAnnotationPresent(Id.class)) continue;
      if (Modifier.isTransient(field.getModifiers())) continue;

      sb.append(SqlStringBuilder.objectStringBuild(field.getName()));
    }
    return sb;
  }

  private StringBuilder columnValueGenerator(String primKey, Object object) {
    var sb = new StringBuilder();

    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (field.getName().equals(primKey)) continue;

      if (Modifier.isTransient(field.getModifiers())) continue;

      sb.append(SqlStringBuilder.clearSymbols(SqlStringBuilder.objectStringBuild(field.getName())));
      sb.append("=?,");
    }
    SqlStringBuilder.deletekLastChar(sb);
    return sb;
  }

  public String insertQuery(Object object)  {
    StringBuilder sqlRequest = new StringBuilder();

    sqlRequest.append("INSERT INTO ")
              .append(object.getClass().getSimpleName().toLowerCase())
              .append(" ")
              .append(SqlStringBuilder.valuesWraper(columnGenerator(object)))
              .append(" ")
              .append("VALUES ");

    Field[] fields = object.getClass().getDeclaredFields();
    int i = 0;

    while (i < fields.length - 1) {
      sqlRequest.append("?,");
      i++;
    }
    SqlStringBuilder.valuesWraper(sqlRequest);
    return sqlRequest.toString();
  }

  public String updateQuery(String primKey, Object object)  {
    StringBuilder sqlRequest = new StringBuilder();

      sqlRequest.append("UPDATE ")
                .append(object.getClass().getSimpleName().toLowerCase())
                .append(" SET ")
                .append(columnValueGenerator(primKey, object))
                .append(" WHERE ")
                .append(primKey)
                .append(" = ?");
    return sqlRequest.toString();
  }

  public String selecQuery(String primKey, Class<?> clazz)  {

    StringBuilder sqlRequest = new StringBuilder();

    sqlRequest.append("SELECT * FROM ")
              .append(clazz.getSimpleName().toLowerCase())
              .append(" WHERE ")
              .append(primKey)
              .append(" = ?");
    return sqlRequest.toString();
  }
}
