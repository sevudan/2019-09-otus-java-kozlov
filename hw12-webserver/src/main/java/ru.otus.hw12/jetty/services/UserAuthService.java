package ru.otus.hw12.jetty.services;

public interface UserAuthService {
  boolean authenticate(String login, String password);
}
