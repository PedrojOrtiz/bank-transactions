package ec.tcs.banktransactions.application.exception;

import jakarta.persistence.EntityNotFoundException;

public class DataNotFound extends EntityNotFoundException {

    public DataNotFound(String message) {
        super(message);
    }

}
