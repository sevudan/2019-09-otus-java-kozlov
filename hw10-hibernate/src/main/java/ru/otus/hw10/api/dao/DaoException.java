package ru.otus.hw10.api.dao;

public class DaoException extends RuntimeException {
  public DaoException(Exception ex) {
    super(ex);
  }
}
