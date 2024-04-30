package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RecordNotFoundException {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class RecordNotFoundException extends RuntimeException{
    }

