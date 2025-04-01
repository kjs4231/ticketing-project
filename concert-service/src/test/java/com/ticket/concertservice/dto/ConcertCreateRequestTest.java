package com.ticket.concertservice.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConcertCreateRequestTest {

    @Test
    void testConcertCreateRequest() {
        LocalDateTime concertDate = LocalDateTime.now().plusDays(7);
        ConcertCreateRequest request = new ConcertCreateRequest("제목", "설명", concertDate, 100);

        assertEquals("제목", request.getTitle());
        assertEquals("설명", request.getDescription());
        assertEquals(concertDate, request.getConcertDate());
        assertEquals(100, request.getCapacity());
    }
}