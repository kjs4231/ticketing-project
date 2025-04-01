package com.ticket.concertservice.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {

    @Test
    void testUserResponse() {
        Long userId = 1L;
        String role = "USER";

        UserResponse response = new UserResponse(userId, role);

        assertEquals(userId, response.getUserId());
        assertEquals(role, response.getRole());
    }

    @Test
    void testIsAdmin() {
        UserResponse adminResponse = new UserResponse(1L, "ADMIN");
        UserResponse userResponse = new UserResponse(2L, "USER");

        assertTrue(adminResponse.isAdmin());
        assertFalse(userResponse.isAdmin());
    }
}