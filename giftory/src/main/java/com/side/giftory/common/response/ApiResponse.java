package com.side.giftory.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ApiResponse<T> {

    private boolean status;
    private T data;
    private String message;
    private String errorCode;

    // 성공 응답
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.status = true;
        response.data = data;
        response.message = null;
        response.errorCode = null;
        return response;
    }

    // 실패 응답
    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.status = false;
        response.data = null;
        response.message = errorCode.getDefaultMessage();
        response.errorCode = errorCode.getCode();
        return response;
    }

    // 실패 응답
    public static <T> ApiResponse<T> error(T data , ErrorCode errorCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.status = false;
        response.data = data;
        response.message = errorCode.getDefaultMessage();
        response.errorCode = errorCode.getCode();
        return response;
    }
}
