package com.ticket.concertservice.domain;

import com.ticket.concertservice.dto.ConcertCreateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String userEmail;

    public void update(ConcertCreateRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.dateTime = request.getDateTime();
    }
}