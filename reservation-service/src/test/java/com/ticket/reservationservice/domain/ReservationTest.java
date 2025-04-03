package com.ticket.reservationservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ReservationTest {

    @Test
    public void testConfirmReservation() {
        // 초기 상태를 PENDING(예시)으로 설정한 후, 예약 확정을 진행합니다.
        Reservation reservation = new Reservation(
                1L,                      // reservationId
                100L,                    // concertId
                "test@example.com",      // userEmail
                2L,                      // quantity
                ReservationStatus.PENDING, // 초기 상태로 PENDING을 사용한다고 가정합니다.
                LocalDateTime.now(),     // reservedAt
                null                     // cancelledAt
        );

        // confirmReservation() 호출 후 상태가 CONFIRMED로 변경되어야 합니다.
        reservation.confirmReservation();
        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus(),
                "예약 확정 후 상태가 CONFIRMED여야 합니다.");
    }

    @Test
    public void testCancelReservation() {
        // 예약 취소 테스트를 위해 초기 상태를 PENDING으로 설정합니다.
        Reservation reservation = new Reservation(
                2L,                      // reservationId
                101L,                    // concertId
                "user@example.com",      // userEmail
                3L,                      // quantity
                ReservationStatus.PENDING, // 초기 상태로 PENDING을 사용한다고 가정합니다.
                LocalDateTime.now(),     // reservedAt
                null                     // cancelledAt
        );

        // 취소 시간을 특정 LocalDateTime 값으로 지정합니다.
        LocalDateTime cancellationTime = LocalDateTime.now().plusDays(1);

        // cancelReservation() 호출 후 상태와 취소 시간이 올바르게 설정되는지 확인합니다.
        reservation.cancelReservation(cancellationTime);
        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus(),
                "예약 취소 후 상태가 CANCELLED여야 합니다.");
        assertEquals(cancellationTime, reservation.getCancelledAt(),
                "취소 시각이 지정한 시간과 같아야 합니다.");
    }
}
