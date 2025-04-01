package com.ticket.concertservice.controller;

import com.ticket.concertservice.domain.Concert;
import com.ticket.concertservice.dto.ConcertCreateRequest;
import com.ticket.concertservice.dto.ConcertResponse;
import com.ticket.concertservice.service.ConcertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ConcertControllerTest {

    @InjectMocks
    private ConcertController concertController;

    @Mock
    private ConcertService concertService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(concertController).build();
    }

    @Test
    void testCreateConcert() throws Exception {
        ConcertCreateRequest request = new ConcertCreateRequest("제목", "설명", LocalDateTime.now().plusDays(7), 100);
        Concert concert = new Concert(1L, 1L, "제목", "설명", LocalDateTime.now().plusDays(7), 100);
        ConcertResponse response = new ConcertResponse(concert);

        when(concertService.createConcert(any(Long.class), any(ConcertCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/concerts")
                        .header("X-USER-ID", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"제목\",\"description\":\"설명\",\"concertDate\":\"" + LocalDateTime.now().plusDays(7).toString() + "\",\"capacity\":100}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.concertId").value(1L))
                .andExpect(jsonPath("$.title").value("제목"));
    }

    @Test
    void testGetConcert() throws Exception {
        Concert concert = new Concert(1L, 1L, "제목", "설명", LocalDateTime.now().plusDays(7), 100);
        when(concertService.findConcertById(1L)).thenReturn(concert);

        mockMvc.perform(get("/concerts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.concertId").value(1L))
                .andExpect(jsonPath("$.title").value("제목"));
    }

    @Test
    void testGetAllConcerts() throws Exception {
        when(concertService.findAllConcerts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/concerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testUpdateConcert() throws Exception {
        ConcertCreateRequest request = new ConcertCreateRequest("수정된 제목", "수정된 설명", LocalDateTime.now().plusDays(14), 200);
        Concert concert = new Concert(1L, 1L, "수정된 제목", "수정된 설명", LocalDateTime.now().plusDays(14), 200);
        ConcertResponse response = new ConcertResponse(concert);

        when(concertService.updateConcert(any(Long.class), any(Long.class), any(ConcertCreateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/concerts/1")
                        .header("X-USER-ID", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"수정된 제목\",\"description\":\"수정된 설명\",\"concertDate\":\"" + LocalDateTime.now().plusDays(14).toString() + "\",\"capacity\":200}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.concertId").value(1L))
                .andExpect(jsonPath("$.title").value("수정된 제목"));
    }

    @Test
    void testDeleteConcert() throws Exception {
        mockMvc.perform(delete("/concerts/1")
                        .header("X-USER-ID", 1L))
                .andExpect(status().isNoContent());
    }
}