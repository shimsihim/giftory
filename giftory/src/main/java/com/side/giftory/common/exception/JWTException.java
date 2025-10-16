package com.side.giftory.common.exception;

import com.side.giftory.common.response.ErrorCode;

public class JWTException extends ApiException {

    public JWTException() {
        super(ErrorCode.INVALID_TOKEN);
    }

}