package com.side.giftory.common.exception;

import com.side.giftory.common.response.ApiResponse;
import com.side.giftory.common.response.ErrorCode;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 비즈니스 예외 처리
    @ExceptionHandler(ApiException.class)
    public ApiResponse<Void> handleApiException(ApiException ex) {
        return ApiResponse.error(ex.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // HTTP BODY 파싱 에러
    public Object handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ApiResponse.error(errors , ErrorCode.BAD_REQUEST);
    }

    // 권한 예외 처리
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ApiResponse<Void> handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex) {
        return ApiResponse.error(ErrorCode.ACCESS_DENIED);
    }

    // 기타 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception ex) {
        return ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}