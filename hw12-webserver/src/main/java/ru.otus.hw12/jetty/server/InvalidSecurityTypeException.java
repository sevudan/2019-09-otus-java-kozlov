package ru.otus.hw12.jetty.server;

class InvalidSecurityTypeException extends RuntimeException {

  InvalidSecurityTypeException(SecurityType securityType) {
    super(String.format("Invalid security type exception: %s", securityType));
  }
}
