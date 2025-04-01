package com.ticket.concertservice.dto;

import com.ticket.concertservice.domain.Concert;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class ConcertResponse {
    private Long concertId;
    private Long userId;
    private String title;
    private String description;
    private LocalDateTime concertDate;
    private int capacity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ConcertResponse(Concert concert) {
        this.concertId = concert.getConcertId();
        this.userId = concert.getUserId();
        this.title = concert.getTitle();
        this.description = concert.getDescription();
        this.concertDate = concert.getConcertDate();
        this.capacity = concert.getCapacity();
        this.createdAt = concert.getCreatedAt();
        this.updatedAt = concert.getUpdatedAt();
    }

    public static ConcertResponse from(Concert concert) {
        return new ConcertResponse(concert);
    }
}