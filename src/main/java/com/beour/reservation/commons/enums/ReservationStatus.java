package com.beour.reservation.commons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {

    PENDING("승인 대기"),
    ACCEPTED("예약 확정"),
    REJECTED("예약 취소"),
    COMPLETED("사용 완료");

    private final String text;
    public static final ReservationStatus DEFAULT = PENDING;

}
