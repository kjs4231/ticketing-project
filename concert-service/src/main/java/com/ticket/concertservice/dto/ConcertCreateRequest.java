package com.ticket.concertservice.dto;

import com.ticket.concertservice.domain.Concert;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ConcertCreateRequest {

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    private String description;

    @NotNull(message = "공연 날짜는 필수입니다")
    @Future(message = "공연 날짜는 미래 날짜여야 합니다")
    private LocalDateTime concertDate;

    @Positive(message = "수용 인원은 양수여야 합니다")
    private int capacity;

    public ConcertCreateRequest() {
    }

    public ConcertCreateRequest(String title, String description, LocalDateTime concertDate, int capacity) {
        this.title = title;
        this.description = description;
        this.concertDate = concertDate;
        this.capacity = capacity;
    }

    public Concert toEntity() {
        return Concert.builder()
                .title(this.title)
                .description(this.description)
                .concertDate(this.concertDate)
                .capacity(this.capacity)
                .build();
    }
}