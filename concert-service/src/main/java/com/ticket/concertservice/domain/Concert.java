package com.ticket.concertservice.domain;

import com.ticket.concertservice.dto.ConcertCreateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long concertId;
    private Long userId;
    private String title;
    private String description;
    private LocalDateTime concertDate;
    private int capacity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Concert() {}

    @Builder
    public Concert(Long concertId, Long userId, String title, String description,
                   LocalDateTime concertDate, int capacity) {
        this.concertId = concertId;  // concertId 추가
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.concertDate = concertDate;
        this.capacity = capacity;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public void update(ConcertCreateRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.concertDate = request.getConcertDate();
        this.capacity = request.getCapacity();
    }
}