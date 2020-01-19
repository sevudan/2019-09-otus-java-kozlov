package ru.otus.hw12.hibernate.dbservice;

public class DbServiceException extends RuntimeException {
  public DbServiceException(Exception e) {
    super(e);
  }
}
