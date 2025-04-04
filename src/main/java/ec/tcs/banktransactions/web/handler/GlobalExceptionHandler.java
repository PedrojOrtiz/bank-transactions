package ec.tcs.banktransactions.web.handler;

import ec.tcs.banktransactions.application.dto.ErrorDTO;
import ec.tcs.banktransactions.application.exception.DataNotFound;
import ec.tcs.banktransactions.application.exception.InsufficientBalance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFound.class)
    public ResponseEntity<?> dataNotFoundExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDTO(LocalDateTime.now(), exception.getMessage(), request.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientBalance.class)
    public ResponseEntity<?> insufficientBalanceExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDTO(LocalDateTime.now(), exception.getMessage(), request.getDescription(false)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDTO(LocalDateTime.now(), exception.getMessage(), request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
