package ru.otus.hw12.jetty.server;

public interface UsersWebServer {
  void start() throws Exception;

  void join() throws Exception;

  void stop() throws Exception;
}
