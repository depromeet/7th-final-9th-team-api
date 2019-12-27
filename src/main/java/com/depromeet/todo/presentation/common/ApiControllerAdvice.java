package com.depromeet.todo.presentation.common;

import com.depromeet.todo.application.ExternalServiceException;
import com.depromeet.todo.infrastructure.spring.security.TodoAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.Principal;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ModelAttribute("memberId")
    public Long resolveMemberId(Principal principal) {
        if (principal instanceof TodoAuthenticationToken) {
            return ((TodoAuthenticationToken) principal).getMemberId();
        }
        return null;
    }

    /**
     * 사용자 요청에 오류가 있는 경우
     */
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleMethodArgumentNotValidException(Exception ex) {
        log.error("Exception occurred while handling arguments", ex);
        return ApiResponse.failureFrom(
                ex.getMessage()
        );
    }

    /**
     * 외부 서비스를 사용할 수 없는 경우
     */
    @ExceptionHandler(ExternalServiceException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ApiResponse handleExternalServiceUnavailable(ExternalServiceException ex) {
        log.error("Exception occurred while using External service", ex);
        return ApiResponse.failureFrom(
                ex.getMessage()
        );
    }

    /**
     * 서버 내부 오류
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleException(Exception ex) {
        log.error("Exception occurred", ex);
        return ApiResponse.failureFrom(
                ex.getMessage()
        );
    }
}
