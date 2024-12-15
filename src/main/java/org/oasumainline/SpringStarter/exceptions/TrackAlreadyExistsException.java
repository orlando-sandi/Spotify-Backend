package org.oasumainline.SpringStarter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class TrackAlreadyExistsException extends TrackException {
    public TrackAlreadyExistsException() {
        super("The given track 'ISRC' already exists");
        this.status = HttpStatus.CONFLICT;
    }
}
