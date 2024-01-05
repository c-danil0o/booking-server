package com.komsije.booking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int grade;
    @Column(nullable = true)
    private String comment;
    @Column(nullable = false)
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account author;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewStatus status;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Host host;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Accommodation accommodation;
}
