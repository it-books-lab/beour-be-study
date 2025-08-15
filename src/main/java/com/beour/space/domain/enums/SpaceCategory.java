package com.beour.space.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpaceCategory {
    CAFE("카페"),
    RESTAURANT("식당"),
    COOKING("쿠킹 공방"),
    LEATHER("가죽 공방"),
    COSTUME("의상 공방"),
    ART("아트 공방"),
    ETC("기타");

    private final String displayName;
}
