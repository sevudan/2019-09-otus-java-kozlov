package ru.otus.jdbc;

import ru.otus.orm.Id;

import java.lang.reflect.Field;

public class DbExecutorHelper<T> {

  public Field getIdField(T objectData) {
    try {
      Field[] fields = objectData.getClass().getDeclaredFields();
      for (Field field : fields) {
        field.setAccessible(true);
        if (isIdAnnotationPresent(field)) {
          return objectData.getClass().getDeclaredField(field.getName());
        }
      }
    } catch (NoSuchFieldException ex){
      ex.printStackTrace();
    }
    throw new IllegalArgumentException("Field with @Id not found!");
  }

  public Field getFieldObject(Field field, T objectData) {
    try {
      field.setAccessible(true);
      return objectData.getClass().getDeclaredField(field.getName());
    } catch (NoSuchFieldException ex) {
      ex.printStackTrace();
    }
    return field;
  }

  public boolean isIdAnnotationPresent(Field field) {
    return field.isAnnotationPresent(Id.class);
  }


}
