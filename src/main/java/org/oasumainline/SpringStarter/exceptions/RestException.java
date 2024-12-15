package org.oasumainline.SpringStarter.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestException extends RuntimeException {
    protected HttpStatus status;
  public RestException(String message) {
    super(message);
  }
}
