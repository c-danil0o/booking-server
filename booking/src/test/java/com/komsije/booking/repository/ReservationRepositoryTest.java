package com.komsije.booking.repository;

import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void shouldSaveReservation(){
        Reservation reservation = new Reservation(null, LocalDate.now().plusDays(5), LocalDate.now(), 3, 3, 300, 1L, 6L, null, ReservationStatus.Pending);
        Reservation savedReservation  = reservationRepository.save(reservation);
        assertThat(savedReservation).usingRecursiveComparison().ignoringFields("id").isEqualTo(reservation);
    }
    @Test
    public void shouldGetReservation(){
        Reservation reservation = new Reservation(null, LocalDate.now().plusDays(5), LocalDate.now(), 3, 3, 300, 1L, 6L, null, ReservationStatus.Pending);
        Reservation savedReservation = reservationRepository.save(reservation);
        Reservation reservation2 = reservationRepository.findById(savedReservation.getId()).orElse(null);
        assertThat(reservation2).usingRecursiveComparison().ignoringFields("id").isEqualTo(reservation);
    }
    @Test
    public void shouldGetReservationWithSameStartDateAndAccommodation(){
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        LocalDate reservationStartDate = LocalDate.now();
        Reservation reservation = new Reservation(null, reservationStartDate, LocalDate.now(), 3, 3, 300, 1L, 6L, accommodation, ReservationStatus.Pending);
        Reservation savedReservation = reservationRepository.save(reservation);
        List<Reservation> reservations = reservationRepository.getIfExists(reservationStartDate,  savedReservation.getAccommodation().getId(), 6L);
        assertThat(reservation).usingRecursiveComparison().ignoringFields("id").isIn(reservations);
    }
    @Test
    public void shouldNotGetReservationWithOtherStartDateAndAccommodation(){
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        LocalDate reservationStartDate = LocalDate.now();
        Reservation reservation = new Reservation(null, reservationStartDate, LocalDate.now(), 3, 3, 300, 1L, 6L, accommodation, ReservationStatus.Cancelled);
        Reservation savedReservation = reservationRepository.save(reservation);
        List<Reservation> reservations = reservationRepository.getIfExists(reservationStartDate.plusDays(12),  savedReservation.getAccommodation().getId(), 6L);
        assertThat(reservation).usingRecursiveComparison().ignoringFields("id").isNotIn(reservations);
    }
    @Test
    public void shouldNotGetReservationWithStartDateAndOtherAccommodation(){
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        LocalDate reservationStartDate = LocalDate.now();
        Reservation reservation = new Reservation(null, reservationStartDate, LocalDate.now(), 3, 3, 300, 1L, 6L, accommodation, ReservationStatus.Cancelled);
        Reservation savedReservation = reservationRepository.save(reservation);
        List<Reservation> reservations = reservationRepository.getIfExists(reservationStartDate,  savedReservation.getAccommodation().getId()+1, 6L);
        assertThat(reservation).usingRecursiveComparison().ignoringFields("id").isNotIn(reservations);
    }
    @Test
    public void shouldNotGetReservationWithStartDateAndAccommodationAndOtherGuest(){
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        LocalDate reservationStartDate = LocalDate.now();
        Long guestId = 6L;
        Reservation reservation = new Reservation(null, reservationStartDate, LocalDate.now(), 3, 3, 300, 1L, guestId, accommodation, ReservationStatus.Cancelled);
        Reservation savedReservation = reservationRepository.save(reservation);
        List<Reservation> reservations = reservationRepository.getIfExists(reservationStartDate,  savedReservation.getAccommodation().getId(), guestId+1);
        assertThat(reservation).usingRecursiveComparison().ignoringFields("id").isNotIn(reservations);
    }

    @Test
    public void shouldReturnPendingReservationForAccommodation(){
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        Reservation reservation = new Reservation(null, LocalDate.now(), LocalDate.now(), 3, 3, 300, 1L, 6L, accommodation, ReservationStatus.Pending);
        Reservation savedReservation = reservationRepository.save(reservation);
        List<Reservation> reservations = reservationRepository.findPendingByAccommodationId(savedReservation.getAccommodation().getId());
        assertThat(reservation).usingRecursiveComparison().ignoringFields("id").isIn(reservations);

    }

    @Test
    public void shouldNotReturnPendingReservationForOtherAccommodation(){
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        Reservation reservation = new Reservation(null, LocalDate.now(), LocalDate.now(), 3, 3, 300, 1L, 6L, accommodation, ReservationStatus.Pending);
        Reservation savedReservation = reservationRepository.save(reservation);
        List<Reservation> reservations = reservationRepository.findPendingByAccommodationId(savedReservation.getAccommodation().getId()+1);
        assertThat(reservation).usingRecursiveComparison().ignoringFields("id").isNotIn(reservations);

    }
    @Test
    public void shouldNotReturnPendingReservationForAccommodationNotPending(){
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        Reservation reservation = new Reservation(null, LocalDate.now(), LocalDate.now(), 3, 3, 300, 1L, 6L, accommodation, ReservationStatus.Denied);
        Reservation savedReservation = reservationRepository.save(reservation);
        List<Reservation> reservations = reservationRepository.findPendingByAccommodationId(savedReservation.getAccommodation().getId());
        assertThat(reservation).usingRecursiveComparison().ignoringFields("id").isNotIn(reservations);

    }


}
