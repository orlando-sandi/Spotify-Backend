package org.oasumainline.SpringStarter.config;

import lombok.extern.slf4j.Slf4j;
import org.oasumainline.SpringStarter.exceptions.RestException;
import org.oasumainline.SpringStarter.exceptions.SpotifyException;
import org.oasumainline.SpringStarter.exceptions.StorageFileNotFoundException;
import org.oasumainline.SpringStarter.exceptions.TrackException;
import org.oasumainline.SpringStarter.models.Track;
import org.oasumainline.SpringStarter.utils.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler({RestException.class})
    public ResponseEntity<ErrorMessage> handleGlobalException(RestException restException) {
        return ResponseEntity.status(restException.getStatus()).body(new ErrorMessage(restException.getMessage(), restException.getStatus().value()));
    }

    @ExceptionHandler({StorageFileNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleStorageException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Image not found", HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler({SpotifyException.class})
    public ResponseEntity<ErrorMessage> handleSpotifyException(){
        return ResponseEntity.internalServerError().body(new ErrorMessage("Error accessing to external service", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
