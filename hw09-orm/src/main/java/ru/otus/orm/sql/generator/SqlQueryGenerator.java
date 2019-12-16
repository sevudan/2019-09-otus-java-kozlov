package ru.otus.orm.sql.generator;

import java.lang.reflect.*;

public class SqlQueryGenerator {

  private StringBuilder columnGenerator(Object object) {
    var sb = new StringBuilder();

    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (Modifier.isTransient(field.getModifiers())) continue;

      sb.append(SqlStringBuilder.objectStringBuild(field.getName()));
    }

    return sb;
  }

  private StringBuilder columnValueGenerator(String primKey, Object object) throws IllegalAccessException {
    var sb = new StringBuilder();

    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (field.getName().equals(primKey)) continue;

      if (Modifier.isTransient(field.getModifiers())) continue;

      sb.append(SqlStringBuilder.clearSymbols(SqlStringBuilder.objectStringBuild(field.getName())));
      sb.append("=");
      sb.append(valuesGenerator(field.get(object)));
    }
    SqlStringBuilder.deletekLastChar(sb);
    return sb;
  }

  private StringBuilder valuesGenerator(Object object) throws IllegalAccessException {
    var sb = new StringBuilder();

    if (object == null) {
      return SqlStringBuilder.objectStringBuild("null");
    }
    if (ChekTypeHelper.isPrimitive(object.getClass())) {
      return  SqlStringBuilder.objectStringBuild(object);

    } if(ChekTypeHelper.isBigDecimal(object.getClass())){
      return  SqlStringBuilder.objectStringBuild(object);

    }else if(ChekTypeHelper.isBoolean(object.getClass())) {
      StringBuilder resultTrue = SqlStringBuilder.objectStringBuild("true");
      StringBuilder resultFalse =  SqlStringBuilder.objectStringBuild("false");
      return (boolean) object ? resultTrue : resultFalse;

    } else if(ChekTypeHelper.isString(object.getClass())){
      return SqlStringBuilder.objectStringWraper(sb, object);

    } else if(ChekTypeHelper.isChar(object.getClass())) {
      return SqlStringBuilder.objectStringWraper(sb, object);

    } else {
      Field[] fields = object.getClass().getDeclaredFields();
      for (Field field : fields) {
        field.setAccessible(true);
        if (Modifier.isTransient(field.getModifiers())) continue;
        Object objectValue = field.get(object);
        sb.append(valuesGenerator(objectValue));
      }
    }
    return sb;
  }

  public String insertQuery(Object object)  {
    StringBuilder sqlRequest = new StringBuilder();
    try {
      sqlRequest.append("INSERT INTO ")
                .append(object.getClass().getSimpleName().toLowerCase())
                .append(" ")
                .append(SqlStringBuilder.valuesWraper(columnGenerator(object)))
                .append(" ")
                .append("VALUES ")
                .append(SqlStringBuilder.valuesWraper(valuesGenerator(object)));

    } catch (IllegalAccessException e) { throw new IllegalArgumentException(); }

    return sqlRequest.toString();
  }

  public String updateQuery(long id, String primKey, Object object)  {
    StringBuilder sqlRequest = new StringBuilder();
    try {
      sqlRequest.append("UPDATE ")
                .append(object.getClass().getSimpleName().toLowerCase())
                .append(" SET ")
                .append(columnValueGenerator(primKey, object))
                .append(" WHERE ")
                .append(primKey)
                .append(" = ")
                .append(id);
    } catch (IllegalAccessException e) { throw new IllegalArgumentException(); }

    return sqlRequest.toString();
  }

  public String selecQuery(long id, String primKey, Class<?> clazz)  {
    StringBuilder sqlRequest = new StringBuilder();

    sqlRequest.append("SELECT * FROM ")
              .append(clazz.getSimpleName().toLowerCase())
              .append(" WHERE ")
              .append(primKey)
              .append(" = ")
              .append(id);
    return sqlRequest.toString();
  }
}
