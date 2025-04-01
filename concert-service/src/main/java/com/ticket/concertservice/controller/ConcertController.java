package com.ticket.concertservice.controller;

import com.ticket.concertservice.domain.Concert;
import com.ticket.concertservice.dto.ConcertCreateRequest;
import com.ticket.concertservice.dto.ConcertResponse;
import com.ticket.concertservice.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/concerts")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;

    @PostMapping
    public ResponseEntity<ConcertResponse> createConcert(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody ConcertCreateRequest request) {
        ConcertResponse response = concertService.createConcert(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{concertId}")
    public ResponseEntity<ConcertResponse> getConcert(@PathVariable Long concertId) {
        Concert concert = concertService.findConcertById(concertId);
        return ResponseEntity.ok(ConcertResponse.from(concert));
    }

    @GetMapping
    public ResponseEntity<List<ConcertResponse>> getAllConcerts() {
        List<Concert> concerts = concertService.findAllConcerts();
        List<ConcertResponse> responses = concerts.stream()
                .map(ConcertResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{concertId}")
    public ResponseEntity<ConcertResponse> updateConcert(
            @PathVariable Long concertId,
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody ConcertCreateRequest request) {
        ConcertResponse response = concertService.updateConcert(concertId, userId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{concertId}")
    public ResponseEntity<Void> deleteConcert(
            @PathVariable Long concertId,
            @RequestHeader("X-USER-ID") Long userId) {
        concertService.deleteConcert(concertId, userId);
        return ResponseEntity.noContent().build();
    }
}