package com.ticket.reservationservice.service;

import com.ticket.reservationservice.dto.ReservationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    public ReservationResponse createReservation(Long concertId, String userEmail, Long quantity) {

        return null;
    }

    public void cancelReservation(Long reservationId, String userEmail) {

    }

    public List<ReservationResponse> findReservationsByUserEmail(String userEmail) {

        return null;
    }

    public List<ReservationResponse> findReservationsByConcertId(Long concertId) {

        return null;
    }

    public boolean checkAvailability(Long concertId, Long quantity) {
        return false;
    }
}
