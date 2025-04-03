package com.ticket.reservationservice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReservationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("예약 엔티티 생성 및 저장 테스트")
    void createAndSaveReservation() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Reservation reservation = new Reservation(
                null,                   // reservationId
                1L,                     // concertId
                "test@example.com",     // userEmail
                2L,                     // quantity
                ReservationStatus.PENDING, // status
                now,                    // reservedAt
                null                    // cancelledAt
        );

        // when
        Reservation savedReservation = entityManager.persistAndFlush(reservation);

        // then
        assertThat(savedReservation.getReservationId()).isNotNull();
        assertThat(savedReservation.getConcertId()).isEqualTo(1L);
        assertThat(savedReservation.getUserEmail()).isEqualTo("test@example.com");
        assertThat(savedReservation.getQuantity()).isEqualTo(2L);
        assertThat(savedReservation.getStatus()).isEqualTo(ReservationStatus.PENDING);
        assertThat(savedReservation.getReservedAt()).isEqualTo(now);
        assertThat(savedReservation.getCancelledAt()).isNull();
    }

    @Test
    @DisplayName("예약 상태 변경 테스트")
    void updateReservationStatus() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Reservation reservation = new Reservation(
                null,
                1L,
                "test@example.com",
                2L,
                ReservationStatus.PENDING,
                now,
                null
        );

        Reservation savedReservation = entityManager.persistAndFlush(reservation);

        // when
        Reservation updatedReservation = new Reservation(
                savedReservation.getReservationId(),
                savedReservation.getConcertId(),
                savedReservation.getUserEmail(),
                savedReservation.getQuantity(),
                ReservationStatus.CONFIRMED,
                savedReservation.getReservedAt(),
                null
        );
        entityManager.merge(updatedReservation);
        entityManager.flush();

        // then
        Reservation foundReservation = entityManager.find(Reservation.class, savedReservation.getReservationId());
        assertThat(foundReservation.getStatus()).isEqualTo(ReservationStatus.CONFIRMED);
    }

    @Test
    @DisplayName("예약 취소 테스트")
    void cancelReservation() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Reservation reservation = new Reservation(
                null,
                1L,
                "test@example.com",
                2L,
                ReservationStatus.CONFIRMED,
                now,
                null
        );

        Reservation savedReservation = entityManager.persistAndFlush(reservation);

        // when
        LocalDateTime cancelTime = LocalDateTime.now();
        // 취소 상태로 변경한 새 객체 생성
        Reservation cancelledReservation = new Reservation(
                savedReservation.getReservationId(),
                savedReservation.getConcertId(),
                savedReservation.getUserEmail(),
                savedReservation.getQuantity(),
                ReservationStatus.CANCELLED,
                savedReservation.getReservedAt(),
                cancelTime
        );
        entityManager.merge(cancelledReservation);
        entityManager.flush();

        // then
        Reservation foundReservation = entityManager.find(Reservation.class, savedReservation.getReservationId());
        assertThat(foundReservation.getStatus()).isEqualTo(ReservationStatus.CANCELLED);
        assertThat(foundReservation.getCancelledAt()).isNotNull();
    }
}