package com.depromeet.todo.infrastructure.kakao;

import com.depromeet.todo.application.ExternalServiceException;

public class KakaoApiFailedException extends ExternalServiceException {
    public KakaoApiFailedException(String message) {
        super(message);
    }
}
