package com.crio.cred.util;

import com.crio.cred.model.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Class ExceptionTracer, to handle different kinds of exceptions.
 *
 * @author harikesh.pallantla
 */
@ControllerAdvice
public class ExceptionTracer extends ResponseEntityExceptionHandler {

    /**
     * Global exception handler.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle method argument type mismatch.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {
        String error = ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();

        ErrorDetails errorDetails =
                new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(errorDetails, new HttpHeaders(),
                errorDetails.getStatus());
    }

    /**
     * On validation error.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> onValidationError(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations()
                .forEach(error -> errors.add(error.getPropertyPath() + ": " + error.getMessage()));
        ErrorDetails errorDetails =
                new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(errorDetails, new HttpHeaders(),
                errorDetails.getStatus());
    }

    /**
     * Handle illegal argument exception.
     *
     * @param ex      the exception
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(RuntimeException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST,
                ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle method argument not valid.
     *
     * @param ex      the ex
     * @param headers the headers
     * @param status  the status
     * @param request the request
     * @return the response entity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));

        ex.getBindingResult().getGlobalErrors().forEach(
                error -> errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));

        ErrorDetails errorDetails =
                new ErrorDetails(HttpStatus.BAD_REQUEST, "Validation Failed", errors);
        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(),
                request);
    }

    /**
     * Handle http request method not supported.
     *
     * @param ex      the ex
     * @param headers the headers
     * @param status  the status
     * @param request the request
     * @return the response entity
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(method -> builder.append(method).append(" "));

        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.METHOD_NOT_ALLOWED,
                ex.getLocalizedMessage(), builder.toString());
        return new ResponseEntity<>(errorDetails, new HttpHeaders(),
                errorDetails.getStatus());
    }
}
