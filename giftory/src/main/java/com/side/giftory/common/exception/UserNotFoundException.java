package com.side.giftory.common.exception;

import com.side.giftory.common.response.ErrorCode;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}