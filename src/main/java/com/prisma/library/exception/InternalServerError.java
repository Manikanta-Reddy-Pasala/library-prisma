package com.prisma.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends RuntimeException {
  private static final long serialVersionUID = -5218143265247846948L;

  public InternalServerError(String message) {
    super(message);
  }
}
