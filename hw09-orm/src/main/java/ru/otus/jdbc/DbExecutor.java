package ru.otus.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.Optional;
import java.lang.NoSuchMethodException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.orm.sql.generator.SqlQueryGenerator;

public class DbExecutor<T> {
  private static Logger logger = LoggerFactory.getLogger(DbExecutor.class);

  private final SqlQueryGenerator sqlGenerator;
  private final Connection connection;
  private final DbExecutorHelper executorHelper;

  public DbExecutor(Connection connection)  {
    this.connection = connection;
    this.sqlGenerator = new SqlQueryGenerator();
    this.executorHelper = new DbExecutorHelper<>();
  }

  private void setGeneratedKeyIntoObject(PreparedStatement pst, T object) throws SQLException {
    try {
      Field idField = executorHelper.getIdField(object);
      idField.setAccessible(true);
      try (ResultSet rs = pst.getGeneratedKeys()) {
        rs.next();
        idField.set(object, rs.getLong(1));
      }
    }catch (IllegalAccessException ex) {
      ex.printStackTrace();
    }
  }

  public void create(T objectData) throws SQLException {
    Field[] fields = objectData.getClass().getDeclaredFields();
    String sql = sqlGenerator.insertQuery(objectData);
    Savepoint savePoint = connection.setSavepoint("createSavePoint");
    try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      try {
        for (int idx = 1; idx < fields.length; idx++) {
          Field fieldObject = executorHelper.getFieldObject(fields[idx], objectData);
          fieldObject.setAccessible(true);
          pst.setObject(idx, fieldObject.get(objectData));
        }
      }catch (IllegalAccessException ex) {
        ex.printStackTrace();
      }
      pst.executeUpdate();
      setGeneratedKeyIntoObject(pst, objectData);
    } catch (SQLException ex) {
        connection.rollback(savePoint);
        logger.error(ex.getMessage(), ex);
        throw ex;
    }
  }

  public void update(T objectData) throws SQLException  {
    Field[] fields = objectData.getClass().getDeclaredFields();
    Field idField = executorHelper.getIdField(objectData);
    idField.setAccessible(true);
    String sql = sqlGenerator.updateQuery(idField.getName(), objectData);
    Savepoint savePoint = connection.setSavepoint("updateSavePoint");

    try (PreparedStatement pst = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS)) {
      try {
          for (int idx = 1; idx < fields.length; idx++) {
            int paramIdx = idx ;
            Field fieldObject = executorHelper.getFieldObject(fields[idx], objectData);
            fieldObject.setAccessible(true);
            pst.setObject(paramIdx, fieldObject.get(objectData));
        }
          pst.setObject(3, idField.get(objectData));
      }catch (IllegalAccessException ex) {
        ex.printStackTrace();
      }
      pst.executeUpdate();
    } catch (SQLException ex) {
        connection.rollback(savePoint);
        logger.error(ex.getMessage(), ex);
        throw ex;
    }
  }

  public void createOrUpdate(T objectData)  throws SQLException, IllegalAccessException {
    Field idField = executorHelper.getIdField(objectData);
    idField.setAccessible(true);
    long id = idField.getLong(objectData);
    if (id == 0) {
      create(objectData);
    } else {
      update(objectData);
    }
  }

  public Optional<T> load(long id, Class<T> clazz)
      throws SQLException , NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
    String idField = "";
    for (Field field: clazz.getDeclaredFields()) {
      field.setAccessible(true);
      if(executorHelper.isIdAnnotationPresent(field)) idField = field.getName();
    }
    String sql = sqlGenerator.selecQuery(idField, clazz);
    T resultObj = clazz.getConstructor().newInstance();

    try (PreparedStatement pst = connection.prepareStatement(sql)) {
      pst.setLong(1, id);
      try (ResultSet rs = pst.executeQuery()) {
      if (rs.next()) {
        for (Field field :  clazz.getDeclaredFields()) {
          field.setAccessible(true);
          int modifiers = field.getModifiers();
        if (Modifier.isTransient(modifiers) || Modifier.isStatic(modifiers)) continue;
          field.set(resultObj, rs.getObject(field.getName()));
        }
        return Optional.of(resultObj);
        }
      }
      return Optional.empty();
    }
  }
}
