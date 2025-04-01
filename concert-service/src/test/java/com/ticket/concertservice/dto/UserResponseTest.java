package com.ticket.concertservice.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {

    @Test
    void testUserResponse() {
        UserResponse response = new UserResponse(1L, "ADMIN");

        assertEquals(1L, response.getUserId());
        assertEquals("ADMIN", response.getRole());
    }
}