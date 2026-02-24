package com.wellsoft.globo.assinaturas.infrastructure.rest.controller.interceptor;

import com.wellsoft.globo.assinaturas.domain.exception.CreateClientException;
import com.wellsoft.globo.assinaturas.domain.exception.ExistentUserException;
import com.wellsoft.globo.assinaturas.domain.exception.PaymentFailedException;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.ErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(ex.getMessage(), ex.getLocalizedMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessage>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getFieldErrors()
                        .stream()
                        .map(ErrorMessage::new)
                        .toList());
    }

    @ExceptionHandler(ExistentUserException.class)
    public ResponseEntity<ErrorMessage> existentUserException(ExistentUserException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(ex.getMessage(), ex.getLocalizedMessage()));
    }

    @ExceptionHandler(CreateClientException.class)
    public ResponseEntity<ErrorMessage> createClientException(CreateClientException ex) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorMessage(ex.getMessage(), ex.getLocalizedMessage()));
    }

    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<ErrorMessage> paymentFailedException(PaymentFailedException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(ex.getMessage(), ex.getLocalizedMessage()));
    }
}
