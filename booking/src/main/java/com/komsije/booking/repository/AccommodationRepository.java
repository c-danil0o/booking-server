package com.komsije.booking.repository;

import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    List<Accommodation> getAccommodationByAccommodationType(AccommodationType type);

    @Query("select a from Accommodation a " +
            "where a.address.city = ?1 " +
            "and ?2 between a.minGuests and a.maxGuests")
    List<Accommodation> getAccommodationsByLocationNumOfGuestsAndDate(String location, int numOfGuests);

//    @Query("select a from Accommodation a " +
//            "where ?1 in a.amenities")
//    List<Accommodation> getAccommodationsByAmenities(List<String> amenities);


}
