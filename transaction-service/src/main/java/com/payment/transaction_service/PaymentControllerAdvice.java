package com.payment.transaction_service;

import com.payment.transaction_service.exception.InsufficientFundsException;
import com.payment.transaction_service.model.out.ErrorResponse;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class PaymentControllerAdvice {

  @ExceptionHandler(InsufficientFundsException.class)
  public ResponseEntity<ErrorResponse> handleInsufficientFunds(InsufficientFundsException ex) {
    return buildErrorResponse("INSUFFICIENT_FUNDS",
        "Not enough balance to complete the transaction", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({CannotGetJdbcConnectionException.class, TransientDataAccessException.class,
      QueryTimeoutException.class})
  public ResponseEntity<ErrorResponse> handleDatabaseDown(Exception ex) {
    log.error("Database is currently unavailable: ", ex);
    return buildErrorResponse("DATABASE_DOWN",
        "Database is currently unavailable, please try again later",
        HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<ErrorResponse> handleDatabaseError(DataAccessException ex) {
    log.error("Database error occurred: ", ex);

    return buildErrorResponse("DATABASE_ERROR", "A database error occurred, please try again",
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleGeneralException(RuntimeException ex) {
    log.error("General error occured: ", ex);
    return buildErrorResponse("GENERAL_ERROR", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(String errorCode, String message,
      HttpStatus status) {
    ErrorResponse errorResponse = new ErrorResponse(
        UUID.randomUUID(),
        errorCode,
        message,
        LocalDateTime.now()
    );
    return new ResponseEntity<>(errorResponse, status);
  }

}
