package com.ticket.concertservice.dto;

import com.ticket.concertservice.domain.Concert;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConcertResponseTest {

    @Test
    void testConcertResponse() {
        LocalDateTime now = LocalDateTime.now();
        Concert concert = new Concert(1L, 1L, "제목", "설명", now, 100);
        ConcertResponse response = new ConcertResponse(concert);

        assertEquals(1L, response.getConcertId());
        assertEquals(1L, response.getUserId());
        assertEquals("제목", response.getTitle());
        assertEquals("설명", response.getDescription());
        assertEquals(now, response.getConcertDate());
        assertEquals(100, response.getCapacity());
        assertNotNull(response.getCreatedAt());
        assertNotNull(response.getUpdatedAt());
    }
}