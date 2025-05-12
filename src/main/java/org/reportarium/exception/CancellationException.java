package org.reportarium.exception;

public class CancellationException extends RuntimeException {
    public CancellationException(String message) {
        super(message);
    }
}
