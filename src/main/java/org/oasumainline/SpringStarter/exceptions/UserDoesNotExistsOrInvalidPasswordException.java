package org.oasumainline.SpringStarter.exceptions;

import org.springframework.http.HttpStatus;

public class UserDoesNotExistsOrInvalidPasswordException extends AuthException {
    public UserDoesNotExistsOrInvalidPasswordException() {
        super("This user does not exists or the password is invalid");
        this.status = HttpStatus.NOT_FOUND;
    }
}
