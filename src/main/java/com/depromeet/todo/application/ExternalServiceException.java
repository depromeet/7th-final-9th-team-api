package com.depromeet.todo.application;

/**
 * 외부 연동 서버가 정상 동작하지 않는 경우 발생하는 exception
 */
public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException() {
    }

    public ExternalServiceException(String message) {
        super(message);
    }

    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalServiceException(Throwable cause) {
        super(cause);
    }

    public ExternalServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
