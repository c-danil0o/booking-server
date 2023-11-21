package com.komsije.booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column( nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean isBlocked;
    @Column(nullable = false)
    private AccountType accountType;

    @ManyToOne
    private Address address;
}
