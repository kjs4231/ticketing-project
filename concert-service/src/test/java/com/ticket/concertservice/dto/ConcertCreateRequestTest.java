package com.ticket.concertservice.dto;

import com.ticket.concertservice.domain.Concert;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConcertCreateRequestTest {

    @Test
    void testConcertCreateRequest() {
        String title = "제목";
        String description = "설명";
        LocalDateTime concertDate = LocalDateTime.now().plusDays(7);
        int capacity = 100;

        ConcertCreateRequest request = new ConcertCreateRequest(title, description, concertDate, capacity);

        assertEquals(title, request.getTitle());
        assertEquals(description, request.getDescription());
        assertEquals(concertDate, request.getConcertDate());
        assertEquals(capacity, request.getCapacity());
    }

    @Test
    void testToEntity() {
        String title = "제목";
        String description = "설명";
        LocalDateTime concertDate = LocalDateTime.now().plusDays(7);
        int capacity = 100;

        ConcertCreateRequest request = new ConcertCreateRequest(title, description, concertDate, capacity);
        Concert concert = request.toEntity();

        assertEquals(title, concert.getTitle());
        assertEquals(description, concert.getDescription());
        assertEquals(concertDate, concert.getConcertDate());
        assertEquals(capacity, concert.getCapacity());
    }
}