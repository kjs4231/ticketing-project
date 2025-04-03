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
        String userEmail = "test@test.com";
        ConcertCreateRequest request = new ConcertCreateRequest(
                "콘서트 제목",
                "콘서트 설명",
                LocalDateTime.now().plusDays(7),
                100L
        );

        Concert concert = Concert.builder()
                .userEmail(userEmail)
                .title(request.getTitle())
                .description(request.getDescription())
                .dateTime(request.getDateTime())
                .capacity(request.getCapacity())
                .build();

        Concert savedConcert = Concert.builder()
                .id(1L)
                .userEmail(userEmail)
                .title(request.getTitle())
                .description(request.getDescription())
                .dateTime(request.getDateTime())
                .capacity(request.getCapacity())
                .build();

        given(concertRepository.save(any(Concert.class))).willReturn(savedConcert);

        // when
        ConcertResponse response = concertService.createConcert(userEmail, request);

        // then
        assertNotNull(response);
        assertEquals(savedConcert.getId(), response.getId());
        assertEquals(request.getTitle(), response.getTitle());
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
                .userEmail(userEmail)
                .title("원래 제목")
                .description("원래 설명")
                .dateTime(LocalDateTime.now().plusDays(7))
                .capacity(100L)
                .build();

        given(concertRepository.findById(concertId)).willReturn(Optional.of(existingConcert));

        // when
        ConcertResponse response = concertService.updateConcert(concertId, userEmail, request);

        // then
        assertNotNull(response);
        assertEquals(request.getTitle(), response.getTitle());
    }

    @Test
    @DisplayName("다른 사용자의 콘서트 수정 시 예외가 발생해야 한다")
    void updateConcert_DifferentUser_ThrowsException() {
        // given
        Long concertId = 1L;
        String creatorEmail = "creator@test.com";
        String differentUserEmail = "other@test.com";

        Concert existingConcert = Concert.builder()
                .id(concertId)
                .userEmail(creatorEmail)
                .title("원래 제목")
                .description("원래 설명")
                .dateTime(LocalDateTime.now().plusDays(7))
                .capacity(100L)
                .build();

        given(concertRepository.findById(concertId)).willReturn(Optional.of(existingConcert));

        // when & then
        ConcertCreateRequest request = new ConcertCreateRequest(
                "수정된 제목",
                "수정된 설명",
                LocalDateTime.now().plusDays(14),
                200L
        );

        assertThrows(IllegalArgumentException.class, () ->
                concertService.updateConcert(concertId, differentUserEmail, request)
        );
    }

    @Test
    @DisplayName("콘서트 삭제가 성공적으로 이루어져야 한다")
    void deleteConcert_Success() {
        // given
        Long concertId = 1L;
        String userEmail = "test@test.com";

        Concert existingConcert = Concert.builder()
                .id(concertId)
                .userEmail(userEmail)
                .title("테스트 콘서트")
                .description("테스트 설명")
                .dateTime(LocalDateTime.now().plusDays(7))
                .capacity(100L)
                .build();

        given(concertRepository.findById(concertId)).willReturn(Optional.of(existingConcert));
        doNothing().when(concertRepository).delete(any(Concert.class));

        // when & then
        assertDoesNotThrow(() -> concertService.deleteConcert(concertId, userEmail));
    }
}