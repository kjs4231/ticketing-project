package com.ticket.concertservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ConcertCreateRequest {
    private String title;
    private String description;
    private LocalDateTime dateTime;
}