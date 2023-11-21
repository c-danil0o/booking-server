package com.komsije.booking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Guest extends Account {
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private int timesCancelled;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {})
    private HashSet<Accommodation> favorites = new HashSet<>();
}
