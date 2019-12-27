package com.depromeet.todo.infrastructure.kma;

import com.depromeet.todo.application.ExternalServiceException;

/**
 * 기상청 날씨 API 사용 중 발생하는 예외
 */
public class KmaWeatherException extends ExternalServiceException {
    public KmaWeatherException(String message) {
        super(message);
    }
}
