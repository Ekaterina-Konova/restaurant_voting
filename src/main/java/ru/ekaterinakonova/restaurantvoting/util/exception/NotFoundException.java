package ru.ekaterinakonova.restaurantvoting.util.exception;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
      super(message);
  }
}
