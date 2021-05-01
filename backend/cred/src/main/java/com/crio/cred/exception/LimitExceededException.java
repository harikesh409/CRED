package com.crio.cred.exception;

/**
 * The type Limit exceeded exception. The exception occurs when the transaction limit of a card is exceeded.
 *
 * @author harikesh.pallantla
 */
public class LimitExceededException extends Exception {
    /**
     * Instantiates a new Limit exceeded exception.
     *
     * @param message the message
     */
    public LimitExceededException(String message) {
        super(message);
    }
}
