package com.crio.cred.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * The type Error details.
 *
 * @author harikesh.pallantla
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorDetails {
    /**
     * The status.
     */
    private HttpStatus status;

    /**
     * The timestamp.
     */
    @SuppressWarnings("unused")
    private ZonedDateTime timestamp;

    /**
     * The message.
     */
    private String message;

    /**
     * The errors.
     */
    private List<String> errors;

    /**
     * Instantiates a new error details.
     *
     * @param status    the status
     * @param timestamp the timestamp
     * @param message   the message
     * @param errors    the errors
     */
    public ErrorDetails(HttpStatus status, ZonedDateTime timestamp, String message,
                        List<String> errors) {
        super();
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
        this.errors = errors;
    }

    /**
     * Instantiates a new error details.
     *
     * @param status  the status
     * @param message the message
     * @param errors  the errors
     */
    public ErrorDetails(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    /**
     * Instantiates a new error details.
     *
     * @param status  the status
     * @param message the message
     * @param error   the error
     */
    public ErrorDetails(HttpStatus status, String message, String... error) {
        super();
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
    }

    /**
     * Gets the timestamp.
     *
     * @return the timestamp
     */
    public ZonedDateTime getTimestamp() {
        return ZonedDateTime.now();
    }
}
