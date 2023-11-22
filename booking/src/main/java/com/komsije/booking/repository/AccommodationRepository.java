package com.komsije.booking.repository;

import com.komsije.booking.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

}
