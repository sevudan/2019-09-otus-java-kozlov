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
  private final DBExexutorHelper executorHelper;

  public DbExecutor(Connection connection)  {
    this.connection = connection;
    this.sqlGenerator = new SqlQueryGenerator();
    this.executorHelper = new DBExexutorHelper<>();
  }


  public void create(T objectData) throws SQLException{

    String sql = sqlGenerator.insertQuery(objectData);

    Savepoint savePoint = connection.setSavepoint("createSavePoint");

    try (PreparedStatement pst = connection.prepareStatement(sql)) {
      pst.execute();
    } catch (SQLException ex) {
      connection.rollback(savePoint);
      logger.error(ex.getMessage(), ex);
      throw ex;
    }
  }

  public void update(T objectData) throws SQLException , IllegalAccessException {

      Field idField = executorHelper.getIdField(objectData);
      idField.setAccessible(true);
      long id = idField.getLong(objectData);

      String sql = sqlGenerator.updateQuery(id, idField.getName(), objectData);
      Savepoint savePoint = connection.setSavepoint("updateSavePoint");
      try (PreparedStatement pst = connection.prepareStatement(sql)) {
        pst.execute();
      } catch (SQLException ex) {
        connection.rollback(savePoint);
        logger.error(ex.getMessage(), ex);
        throw ex;
      }
    }

  public void createOrUpdate(T objectData)  throws SQLException , NoSuchMethodException,
          InstantiationException, IllegalAccessException, InvocationTargetException {

      Field idField = executorHelper.getIdField(objectData);
      idField.setAccessible(true);
      long id = idField.getLong(objectData);
      Object loadObject = load(id, (Class<T>) objectData.getClass());
      if (loadObject.equals(Optional.empty())) {
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
    String sql = sqlGenerator.selecQuery(id, idField, clazz);

    T resultObj = clazz.getConstructor().newInstance();

    try (PreparedStatement pst = connection.prepareStatement(sql)) {
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
