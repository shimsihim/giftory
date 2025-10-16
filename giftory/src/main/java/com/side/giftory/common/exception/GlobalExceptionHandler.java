package com.side.giftory.common.exception;

import com.side.giftory.common.response.ApiResponse;
import com.side.giftory.common.response.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 비즈니스 예외 처리
    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleApiException(ApiException ex) {
        return ApiResponse.error(ex.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // HTTP BODY 파싱 에러
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ApiResponse.error(errors , ErrorCode.BAD_REQUEST);
    }

    // 권한 예외 처리
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<Void> handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex) {
        return ApiResponse.error(ErrorCode.ACCESS_DENIED);
    }

    // 기타 모든 예외 처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleException(Exception ex) {
        return ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /* // 에러의 경우 ApiResponse가 아니라 기존대로 ResponseEntity로 묶는게 맞는 설계로 보이긴 함.. 고려 필요..
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException e) {
        return ResponseEntity.status(404).body(ApiResponse.fail(e.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e) {
        var msg = e.getBindingResult().getFieldErrors().stream()
        .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
        .findFirst().orElse("Validation error");
        return ResponseEntity.badRequest().body(ApiResponse.fail(msg));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOthers(Exception e) {
        return ResponseEntity.internalServerError().body(ApiResponse.fail(e.getMessage()));
    }
     */
}