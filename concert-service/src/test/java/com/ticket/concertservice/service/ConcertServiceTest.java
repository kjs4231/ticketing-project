package com.ticket.concertservice.service;

import com.ticket.concertservice.domain.Concert;
import com.ticket.concertservice.dto.ConcertCreateRequest;
import com.ticket.concertservice.dto.ConcertResponse;
import com.ticket.concertservice.repository.ConcertRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @InjectMocks
    private ConcertService concertService;

    @Mock
    private ConcertRepository concertRepository;

    @Test
    @DisplayName("콘서트 생성이 성공적으로 이루어져야 한다")
    void createConcert_Success() {
        // given
        String userEmail = "test@test.com";
        ConcertCreateRequest request = new ConcertCreateRequest(
                "콘서트 제목",
                "콘서트 설명",
                LocalDateTime.now().plusDays(7),
                100L
        );

        Concert savedConcert = Concert.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dateTime(request.getDateTime())
                .userEmail(userEmail)
                .capacity(request.getCapacity())
                .build();

        given(concertRepository.save(any(Concert.class))).willReturn(savedConcert);

        // when
        ConcertResponse response = concertService.createConcert(userEmail, request);

        // then
        assertNotNull(response);
        assertEquals(savedConcert.getTitle(), response.getTitle());
        assertEquals(savedConcert.getDescription(), response.getDescription());
        verify(concertRepository).save(any(Concert.class));
    }

    @Test
    @DisplayName("모든 콘서트 목록을 조회할 수 있다")
    void findAllConcerts_Success() {
        // given
        Concert concert1 = Concert.builder()
                .title("콘서트1")
                .description("설명1")
                .dateTime(LocalDateTime.now().plusDays(1))
                .userEmail("test1@test.com")
                .capacity(100L)
                .build();

        Concert concert2 = Concert.builder()
                .title("콘서트2")
                .description("설명2")
                .dateTime(LocalDateTime.now().plusDays(2))
                .userEmail("test2@test.com")
                .capacity(200L)
                .build();

        given(concertRepository.findAll()).willReturn(Arrays.asList(concert1, concert2));

        // when
        List<Concert> concerts = concertService.findAllConcerts();

        // then
        assertEquals(2, concerts.size());
        assertEquals("콘서트1", concerts.get(0).getTitle());
        assertEquals("콘서트2", concerts.get(1).getTitle());
    }

    @Test
    @DisplayName("특정 콘서트를 ID로 조회할 수 있다")
    void findConcertById_Success() {
        // given
        Long concertId = 1L;
        Concert concert = Concert.builder()
                .id(concertId)
                .title("콘서트")
                .description("설명")
                .dateTime(LocalDateTime.now().plusDays(7))
                .userEmail("test@test.com")
                .capacity(100L)
                .build();

        given(concertRepository.findById(concertId)).willReturn(Optional.of(concert));

        // when
        Concert result = concertService.findConcertById(concertId);

        // then
        assertNotNull(result);
        assertEquals(concert.getTitle(), result.getTitle());
        assertEquals(concertId, result.getId());
    }

    @Test
    @DisplayName("사용자의 콘서트 목록을 조회할 수 있다")
    void findConcertsByUserEmail_Success() {
        // given
        String userEmail = "test@test.com";
        Concert concert1 = Concert.builder()
                .title("콘서트1")
                .description("설명1")
                .dateTime(LocalDateTime.now().plusDays(1))
                .userEmail(userEmail)
                .capacity(100L)
                .build();

        Concert concert2 = Concert.builder()
                .title("콘서트2")
                .description("설명2")
                .dateTime(LocalDateTime.now().plusDays(2))
                .userEmail(userEmail)
                .capacity(200L)
                .build();

        given(concertRepository.findByUserEmailOrderByDateTimeDesc(userEmail))
                .willReturn(Arrays.asList(concert2, concert1));

        // when
        List<Concert> concerts = concertService.findConcertsByUserEmail(userEmail);

        // then
        assertEquals(2, concerts.size());
        assertEquals("콘서트2", concerts.get(0).getTitle());
        assertEquals("콘서트1", concerts.get(1).getTitle());
    }

    @Test
    @DisplayName("콘서트 수정이 성공적으로 이루어져야 한다")
    void updateConcert_Success() {
        // given
        Long concertId = 1L;
        String userEmail = "test@test.com";
        ConcertCreateRequest request = new ConcertCreateRequest(
                "수정된 제목",
                "수정된 설명",
                LocalDateTime.now().plusDays(14),
                200L
        );

        Concert existingConcert = Concert.builder()
                .id(concertId)
                .title("원래 제목")
                .description("원래 설명")
                .dateTime(LocalDateTime.now().plusDays(7))
                .userEmail(userEmail)
                .capacity(100L)
                .build();

        given(concertRepository.findById(concertId)).willReturn(Optional.of(existingConcert));

        // when
        ConcertResponse response = concertService.updateConcert(concertId, userEmail, request);

        // then
        assertNotNull(response);
        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getDescription(), response.getDescription());
        assertEquals(request.getCapacity(), response.getCapacity());
    }

    @Test
    @DisplayName("콘서트 삭제가 성공적으로 이루어져야 한다")
    void deleteConcert_Success() {
        // given
        Long concertId = 1L;
        String userEmail = "test@test.com";

        Concert existingConcert = Concert.builder()
                .id(concertId)
                .title("테스트 콘서트")
                .description("테스트 설명")
                .dateTime(LocalDateTime.now().plusDays(7))
                .userEmail(userEmail)
                .capacity(100L)
                .build();

        given(concertRepository.findById(concertId)).willReturn(Optional.of(existingConcert));
        doNothing().when(concertRepository).delete(any(Concert.class));

        // when & then
        assertDoesNotThrow(() -> concertService.deleteConcert(concertId, userEmail));
        verify(concertRepository).delete(existingConcert);
    }
}