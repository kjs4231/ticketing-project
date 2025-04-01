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
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

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
        Long userId = 1L;
        ConcertCreateRequest request = new ConcertCreateRequest(
                "콘서트 제목",
                "콘서트 설명",
                LocalDateTime.now().plusDays(7),
                100
        );

        Concert concert = Concert.builder()
                .userId(userId)
                .title(request.getTitle())
                .description(request.getDescription())
                .concertDate(request.getConcertDate())
                .capacity(request.getCapacity())
                .build();

        Concert savedConcert = Concert.builder()
                .concertId(1L)
                .userId(userId)
                .title(request.getTitle())
                .description(request.getDescription())
                .concertDate(request.getConcertDate())
                .capacity(request.getCapacity())
                .build();

        given(concertRepository.save(any(Concert.class))).willReturn(savedConcert);

        // when
        ConcertResponse response = concertService.createConcert(userId, request);

        // then
        assertNotNull(response);
        assertEquals(savedConcert.getConcertId(), response.getConcertId());
        assertEquals(request.getTitle(), response.getTitle());
    }

    @Test
    @DisplayName("콘서트 수정이 성공적으로 이루어져야 한다")
    void updateConcert_Success() {
        // given
        Long concertId = 1L;
        Long userId = 1L;
        ConcertCreateRequest request = new ConcertCreateRequest(
                "수정된 제목",
                "수정된 설명",
                LocalDateTime.now().plusDays(14),
                200
        );

        Concert existingConcert = Concert.builder()
                .concertId(concertId)
                .userId(userId)
                .title("원래 제목")
                .description("원래 설명")
                .concertDate(LocalDateTime.now().plusDays(7))
                .capacity(100)
                .build();

        given(concertRepository.findById(concertId)).willReturn(Optional.of(existingConcert));

        // when
        ConcertResponse response = concertService.updateConcert(concertId, userId, request);

        // then
        assertNotNull(response);
        assertEquals(request.getTitle(), response.getTitle());
    }

    @Test
    @DisplayName("다른 사용자의 콘서트 수정 시 예외가 발생해야 한다")
    void updateConcert_DifferentUser_ThrowsException() {
        // given
        Long concertId = 1L;
        Long creatorId = 1L;
        Long differentUserId = 2L;

        Concert existingConcert = Concert.builder()
                .concertId(concertId)
                .userId(creatorId)
                .title("원래 제목")
                .description("원래 설명")
                .concertDate(LocalDateTime.now().plusDays(7))
                .capacity(100)
                .build();

        given(concertRepository.findById(concertId)).willReturn(Optional.of(existingConcert));

        // when & then
        ConcertCreateRequest request = new ConcertCreateRequest(
                "수정된 제목",
                "수정된 설명",
                LocalDateTime.now().plusDays(14),
                200
        );

        assertThrows(AccessDeniedException.class, () ->
                concertService.updateConcert(concertId, differentUserId, request)
        );
    }

    @Test
    @DisplayName("콘서트 삭제가 성공적으로 이루어져야 한다")
    void deleteConcert_Success() {
        // given
        Long concertId = 1L;
        Long userId = 1L;

        Concert existingConcert = Concert.builder()
                .concertId(concertId)
                .userId(userId)
                .title("테스트 콘서트")
                .description("테스트 설명")
                .concertDate(LocalDateTime.now().plusDays(7))
                .capacity(100)
                .build();

        given(concertRepository.findById(concertId)).willReturn(Optional.of(existingConcert));
        doNothing().when(concertRepository).delete(any(Concert.class));

        // when & then
        assertDoesNotThrow(() -> concertService.deleteConcert(concertId, userId));
    }
}