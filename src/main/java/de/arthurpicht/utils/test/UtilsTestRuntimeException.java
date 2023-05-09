package de.arthurpicht.utils.test;

public class UtilsTestRuntimeException extends RuntimeException {

    public UtilsTestRuntimeException() {
    }

    public UtilsTestRuntimeException(String message) {
        super(message);
    }

    public UtilsTestRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilsTestRuntimeException(Throwable cause) {
        super(cause);
    }

    public UtilsTestRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
