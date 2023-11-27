package com.komsije.booking.repository;

import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    List<Accommodation> getAccommodationByAccommodationType(AccommodationType type);
}
