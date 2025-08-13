package com.beour.global.exception.error.errorcode;

import com.beour.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BannerErrorCode implements ErrorCode {

    BANNER_NOT_EXIST(404, "배너가 비어있습니다.");


    private final Integer code;
    private final String message;
}
