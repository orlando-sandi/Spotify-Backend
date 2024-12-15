package org.oasumainline.SpringStarter.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends AuthException {

    public UserAlreadyExistsException() {
        super("This user already exists");
        this.status = HttpStatus.CONFLICT;
    }
}
