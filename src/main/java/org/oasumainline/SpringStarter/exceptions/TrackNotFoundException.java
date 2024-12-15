package org.oasumainline.SpringStarter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TrackNotFoundException extends TrackException {
    public TrackNotFoundException(String isrc) {
        super("The track '" + isrc + "' does not exist in the database");
        this.status = HttpStatus.NOT_FOUND;
    }
}
