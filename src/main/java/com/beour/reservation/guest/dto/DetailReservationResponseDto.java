package com.beour.reservation.guest.dto;

import com.beour.reservation.commons.entity.Reservation;
import com.beour.reservation.commons.enums.ReservationStatus;
import com.beour.reservation.commons.enums.UsagePurpose;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DetailReservationResponseDto {

    private Long reservationId;
    private String guestName;
    private String guestPhoneNumber;
    private String spaceName;
    private String spacePhoneNumber;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int price;
    private int guestCount;
    private ReservationStatus status;
    private UsagePurpose usagePurpose;
    private String requestMessage;

    @Builder
    private DetailReservationResponseDto(Long reservationId, String guestName,
        String guestPhoneNumber, String spaceName, String spacePhoneNumber, LocalDate date,
        LocalTime startTime, LocalTime endTime, int price, int guestCount, ReservationStatus status,
        UsagePurpose usagePurpose, String requestMessage) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.guestPhoneNumber = guestPhoneNumber;
        this.spaceName = spaceName;
        this.spacePhoneNumber = spacePhoneNumber;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.guestCount = guestCount;
        this.status = status;
        this.usagePurpose = usagePurpose;
        this.requestMessage = requestMessage;
    }

    public static DetailReservationResponseDto of(Reservation reservation) {
        return DetailReservationResponseDto.builder()
            .reservationId(reservation.getId())
            .guestName(reservation.getGuest().getName())
            .guestPhoneNumber(reservation.getGuest().getPhone())
            .spaceName(reservation.getSpace().getName())
            .spacePhoneNumber(reservation.getHost().getPhone())
            .date(reservation.getDate())
            .startTime(reservation.getStartTime())
            .endTime(reservation.getEndTime())
            .price(reservation.getPrice())
            .guestCount(reservation.getGuestCount())
            .status(reservation.getStatus())
            .usagePurpose(reservation.getUsagePurpose())
            .requestMessage(reservation.getRequestMessage())
            .build();
    }
}
