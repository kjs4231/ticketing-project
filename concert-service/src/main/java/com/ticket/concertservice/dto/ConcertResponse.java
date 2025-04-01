package com.ticket.concertservice.dto;

import com.ticket.concertservice.domain.Concert;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ConcertResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private String userEmail;

    public static ConcertResponse from(Concert concert) {
        return ConcertResponse.builder()
                .id(concert.getId())
                .title(concert.getTitle())
                .description(concert.getDescription())
                .dateTime(concert.getDateTime())
                .userEmail(concert.getUserEmail())
                .build();
    }
}