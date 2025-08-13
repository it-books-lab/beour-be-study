package com.beour.global.exception.exceptionType;

import com.beour.global.exception.error.ErrorCode;

public class BannerNotFoundException extends RuntimeException {

    private final Integer errorCode;

    public BannerNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
