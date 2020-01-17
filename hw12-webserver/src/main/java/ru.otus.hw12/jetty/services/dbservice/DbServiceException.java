package ru.otus.hw12.jetty.services.dbservice;

public class DbServiceException extends RuntimeException {
  public DbServiceException(Exception e) {
    super(e);
  }
}
