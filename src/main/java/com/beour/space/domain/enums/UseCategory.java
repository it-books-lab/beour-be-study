package com.beour.space.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UseCategory {
    MEETING("단체 모임"),
    COOKING("요리 연습"),
    BARISTA("바리스타 실습"),
    FLEA_MARKET("플리마켓"),
    FILMING("촬영"),
    ETC("기타");

    private final String displayName;
}
