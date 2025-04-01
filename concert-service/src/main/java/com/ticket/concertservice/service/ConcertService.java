package com.ticket.concertservice.service;

import com.ticket.concertservice.domain.Concert;
import com.ticket.concertservice.dto.ConcertCreateRequest;
import com.ticket.concertservice.dto.ConcertResponse;
import com.ticket.concertservice.repository.ConcertRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConcertService {
    private final ConcertRepository concertRepository;

    @Transactional
    public ConcertResponse createConcert(Long userId, ConcertCreateRequest request) {
        validateConcertDate(request.getConcertDate());

        Concert concert = Concert.builder()
                .userId(userId)
                .title(request.getTitle())
                .description(request.getDescription())
                .concertDate(request.getConcertDate())
                .capacity(request.getCapacity())
                .build();

        Concert savedConcert = concertRepository.save(concert);
        return ConcertResponse.from(savedConcert);
    }

    @Transactional
    public ConcertResponse updateConcert(Long concertId, Long userId, ConcertCreateRequest request) {
        Concert concert = findConcertById(concertId);
        validateConcertOwner(concert, userId);
        validateConcertDate(request.getConcertDate());

        concert.update(request);
        return ConcertResponse.from(concert);
    }

    @Transactional
    public void deleteConcert(Long concertId, Long userId) {
        Concert concert = findConcertById(concertId);
        validateConcertOwner(concert, userId);
        concertRepository.delete(concert);
    }

    public Concert findConcertById(Long concertId) {
        return concertRepository.findById(concertId)
                .orElseThrow(() -> new EntityNotFoundException("콘서트를 찾을 수 없습니다."));
    }

    public List<Concert> findAllConcerts() {
        return concertRepository.findAll();
    }

    private void validateConcertDate(LocalDateTime concertDate) {
        if (concertDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("공연 날짜는 현재보다 이후여야 합니다.");
        }
    }

    private void validateConcertOwner(Concert concert, Long userId) {
        if (!concert.getUserId().equals(userId)) {
            throw new AccessDeniedException("해당 콘서트에 대한 권한이 없습니다.");
        }
    }
}