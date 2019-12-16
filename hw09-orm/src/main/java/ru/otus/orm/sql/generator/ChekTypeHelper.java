package ru.otus.orm.sql.generator;

import java.math.BigDecimal;

final class ChekTypeHelper {

  private static boolean isByte(Class<?> clazz) {
    return clazz.equals(Byte.class);
  }

  private static boolean isShort(Class<?> clazz) {
    return clazz.equals(Short.class) ;
  }

  private static boolean isInt(Class<?> clazz) { return clazz.equals(Integer.class); }

  private static boolean isLong(Class<?> clazz) {
    return clazz.equals(Long.class);
  }

  private static boolean isFloat(Class<?> clazz) {
    return clazz.equals(Float.class);
  }

  private static boolean isDouble(Class<?> clazz) {
    return clazz.equals(Double.class);
  }

  public static boolean isChar(Class<?> clazz) {
    return clazz.equals(Character.class);
  }

  public static boolean isString(Class<?> clazz) {
    return clazz.equals(String.class);
  }

  public static boolean isBoolean(Class<?> clazz) {
    return clazz.equals(Boolean.class);
  }

  public static boolean isBigDecimal(Class<?> clazz) {
    return clazz.equals(BigDecimal.class);
  }

  public static boolean isPrimitive(Class<?> clazz) {
    return isByte(clazz) || isShort(clazz)  || isInt(clazz)
        || isLong(clazz) || isFloat(clazz) || isDouble(clazz);
  }
}