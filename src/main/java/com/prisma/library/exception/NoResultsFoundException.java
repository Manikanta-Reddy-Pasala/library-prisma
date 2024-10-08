package com.prisma.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoResultsFoundException extends RuntimeException {
  private static final long serialVersionUID = -5218143265247846948L;

  public NoResultsFoundException(String message) {
    super(message);
  }
}
