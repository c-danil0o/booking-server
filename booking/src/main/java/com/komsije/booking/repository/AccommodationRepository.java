package com.komsije.booking.repository;

import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    List<Accommodation> getAccommodationByAccommodationType(AccommodationType type);

    @Query("SELECT a FROM Accommodation a WHERE :guests BETWEEN a.minGuests AND a.maxGuests AND a.status='Active'")
    List<Accommodation> getAccommodationsByNumberOfGuests(@Param("guests") int guests);

    @Query("SELECT a FROM Accommodation a WHERE a.status='Active'")
    List<Accommodation> getActive();


/*    @Query("select a from Accommodation a " +
            "where a.address.city = ?1 " +
            "and ?2 between a.minGuests and a.maxGuests")*/

    @Query("SELECT DISTINCT a FROM Accommodation a " +
            "JOIN FETCH a.availability timeSlot " +
            "WHERE (:location is null or a.address.city = :location) " +
            "AND (:numOfGuests is null or a.maxGuests >= :numOfGuests) " +
            "AND (cast(:endDate as date) is null or timeSlot.startDate <= :endDate) " +
            "AND (cast(:startDate as date) is null or timeSlot.endDate >= :startDate) ")
    List<Accommodation> getAccommodationsByLocationNumOfGuestsAndDate(String location, Integer numOfGuests,
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate);

    //    @Query("select a from Accommodation a " +
//            "where ?1 in a.amenities")
//    @Query("SELECT a FROM Accommodation a JOIN a.amenities amenity WHERE amenity IN :amenities")
//    @Query("SELECT a FROM Accommodation a WHERE " +
//            "ALL (amenity IN :amenities WHERE amenity MEMBER OF a.amenities)")
//    @Query("SELECT a FROM Accommodation a WHERE :amenities intersect  a.amenities")
    @Query("SELECT a FROM Accommodation a JOIN a.amenities amenity WHERE amenity IN :amenities")
    List<Accommodation> getAccommodationsByAmenities(@Param("amenities") List<String> amenities);
    List<Accommodation> findByHostId(Long hostId);

    @Query("SELECT a FROM Accommodation a where a.status!='Active' and a.status!='Inactive'")
    List<Accommodation> findUnapproved();

}
