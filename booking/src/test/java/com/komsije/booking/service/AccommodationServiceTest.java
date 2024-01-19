package com.komsije.booking.service;

import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.ReservationAlreadyExistsException;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.TimeSlot;
import com.komsije.booking.repository.AccommodationRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AccommodationServiceTest {
    @Mock
    private AccommodationRepository accommodationRepository;

    @InjectMocks
    private AccommodationServiceImpl accommodationService;
    private List<TimeSlot> availability;
    private LocalDate referenceDate = LocalDate.now().plusDays(10);
    private final Long VALID_ACCOMMODATION_ID = 10L;
    private final Long INVALID_ACCOMMODATION_ID = 20L;

    public static List<TimeSlot> deepCopyUsingSerialization(List<TimeSlot> timeSlots) {
        return timeSlots.stream().map(SerializationUtils::clone).collect(Collectors.toList());
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        availability = new ArrayList<>();
        availability.addAll(Arrays.asList(new TimeSlot(1L, referenceDate, referenceDate.plusDays(10), 200, false),          // 0 - 10
                new TimeSlot(2L, referenceDate.plusDays(10), referenceDate.plusDays(15), 300, false),           // 10 - 15
                new TimeSlot(4L, referenceDate.plusDays(23), referenceDate.plusDays(30), 300, false),          // 23 - 30
                new TimeSlot(3L, referenceDate.plusDays(15), referenceDate.plusDays(20), 400, false)));       // 15 - 20
    }

    @Test
    public void reserveTimeslot_ShouldReserveExactTimeslot() {
        Accommodation accommodation = new Accommodation();
        List<TimeSlot> copyTimeslots = deepCopyUsingSerialization(availability);
        accommodation.setAvailability(copyTimeslots);
        availability.sort((item1, item2) -> {
            return Math.toIntExact(item1.getStartDate().toEpochDay() - item2.getStartDate().toEpochDay());
        });
        when(accommodationRepository.findById(VALID_ACCOMMODATION_ID)).thenReturn(Optional.of(accommodation));
        accommodationService.reserveTimeslot(VALID_ACCOMMODATION_ID, referenceDate.plusDays(10), referenceDate.plusDays(15));
        verify(accommodationRepository).findById(VALID_ACCOMMODATION_ID);
        verify(accommodationRepository).save(accommodation);
        verifyNoMoreInteractions(accommodationRepository);
        for (int i = 0; i < copyTimeslots.size(); i++) {
            if (copyTimeslots.get(i).getId().equals(2L)) {
                assertThat(copyTimeslots.get(i)).usingRecursiveComparison().ignoringFields("isOccupied").isEqualTo(availability.get(i));
                assertTrue(copyTimeslots.get(i).isOccupied());
                continue;

            }
            assertThat(copyTimeslots.get(i)).usingRecursiveComparison().isEqualTo(availability.get(i));
        }
    }

    @Test
    public void reserveTimeSlot_ShouldReserveOneWholeAndHalfOfOneAfter(){
        Accommodation accommodation = new Accommodation();
        List<TimeSlot> copyTimeslots = deepCopyUsingSerialization(availability);
        accommodation.setAvailability(copyTimeslots);
        when(accommodationRepository.findById(VALID_ACCOMMODATION_ID)).thenReturn(Optional.of(accommodation));
        accommodationService.reserveTimeslot(VALID_ACCOMMODATION_ID, referenceDate.plusDays(10), referenceDate.plusDays(17));
        verify(accommodationRepository).findById(VALID_ACCOMMODATION_ID);
        verify(accommodationRepository).save(accommodation);
        assertEquals(5, copyTimeslots.size());
        availability.removeIf(timeSlot -> timeSlot.getId().equals(3L));
        availability.add(new TimeSlot(null, referenceDate.plusDays(15), referenceDate.plusDays(17), 400, true));
        availability.add(new TimeSlot(null, referenceDate.plusDays(17), referenceDate.plusDays(20), 400, false));
        availability.removeIf(timeSlot -> timeSlot.getStartDate().equals(referenceDate.plusDays(10)));
        availability.add(new TimeSlot(2L, referenceDate.plusDays(10), referenceDate.plusDays(15), 300, true));
        verifyNoMoreInteractions(accommodationRepository);
        availability.sort((item1, item2) -> {
            return Math.toIntExact(item1.getStartDate().toEpochDay() - item2.getStartDate().toEpochDay());
        });
        copyTimeslots.sort((item1, item2) -> {
            return Math.toIntExact(item1.getStartDate().toEpochDay() - item2.getStartDate().toEpochDay());
        });
        for (int i =0; i<availability.size(); i++){
            assertThat(availability.get(i)).usingRecursiveComparison().ignoringFields("id").isEqualTo(copyTimeslots.get(i));
        }
    }
    @Test
    public void reserveTimeSlot_ShouldReserveOneWholeAndHalfOfOneBefore(){
        Accommodation accommodation = new Accommodation();
        List<TimeSlot> copyTimeslots = deepCopyUsingSerialization(availability);
        accommodation.setAvailability(copyTimeslots);
        when(accommodationRepository.findById(VALID_ACCOMMODATION_ID)).thenReturn(Optional.of(accommodation));
        accommodationService.reserveTimeslot(VALID_ACCOMMODATION_ID, referenceDate.plusDays(13), referenceDate.plusDays(20));
        verify(accommodationRepository).findById(VALID_ACCOMMODATION_ID);
        verify(accommodationRepository).save(accommodation);
        assertEquals(5, copyTimeslots.size());
        availability.removeIf(timeSlot -> timeSlot.getId().equals(2L));
        availability.removeIf(timeSlot -> timeSlot.getId().equals(3L));
        availability.add(new TimeSlot(null, referenceDate.plusDays(13), referenceDate.plusDays(15), 300, true));
        availability.add(new TimeSlot(null, referenceDate.plusDays(10), referenceDate.plusDays(13), 300, false));
        availability.add(new TimeSlot(null, referenceDate.plusDays(15), referenceDate.plusDays(20), 400, true));
        verifyNoMoreInteractions(accommodationRepository);
        availability.sort((item1, item2) -> {
            return Math.toIntExact(item1.getStartDate().toEpochDay() - item2.getStartDate().toEpochDay());
        });
        copyTimeslots.sort((item1, item2) -> {
            return Math.toIntExact(item1.getStartDate().toEpochDay() - item2.getStartDate().toEpochDay());
        });
        for (int i =0; i<availability.size(); i++){
            assertThat(availability.get(i)).usingRecursiveComparison().ignoringFields("id").isEqualTo(copyTimeslots.get(i));
        }
    }
    @Test
    public void reserveTimeSlot_ShouldReserveCentralPartOfOneTimeSlot(){
        Accommodation accommodation = new Accommodation();
        List<TimeSlot> copyTimeslots = deepCopyUsingSerialization(availability);
        accommodation.setAvailability(copyTimeslots);
        when(accommodationRepository.findById(VALID_ACCOMMODATION_ID)).thenReturn(Optional.of(accommodation));
        accommodationService.reserveTimeslot(VALID_ACCOMMODATION_ID, referenceDate.plusDays(3), referenceDate.plusDays(7));
        verify(accommodationRepository).findById(VALID_ACCOMMODATION_ID);
        verify(accommodationRepository).save(accommodation);
        verifyNoMoreInteractions(accommodationRepository);
        availability.removeIf(timeSlot -> timeSlot.getId().equals(1L));
        availability.add(new TimeSlot(null, referenceDate.plusDays(0), referenceDate.plusDays(3), 200, false));
        availability.add(new TimeSlot(null, referenceDate.plusDays(3), referenceDate.plusDays(7), 200, true));
        availability.add(new TimeSlot(null, referenceDate.plusDays(7), referenceDate.plusDays(10), 200, false));
        availability.sort((item1, item2) -> {
            return Math.toIntExact(item1.getStartDate().toEpochDay() - item2.getStartDate().toEpochDay());
        });
        copyTimeslots.sort((item1, item2) -> {
            return Math.toIntExact(item1.getStartDate().toEpochDay() - item2.getStartDate().toEpochDay());
        });
        for (int i =0; i<availability.size(); i++){
            assertThat(availability.get(i)).usingRecursiveComparison().ignoringFields("id").isEqualTo(copyTimeslots.get(i));
        }
    }

    @Test
    public void reserveTimeSlot_ShouldReserveFirstPartOfOneTimeSlot(){
        Accommodation accommodation = new Accommodation();
        List<TimeSlot> copyTimeslots = deepCopyUsingSerialization(availability);
        accommodation.setAvailability(copyTimeslots);
        when(accommodationRepository.findById(VALID_ACCOMMODATION_ID)).thenReturn(Optional.of(accommodation));
        accommodationService.reserveTimeslot(VALID_ACCOMMODATION_ID, referenceDate.plusDays(10), referenceDate.plusDays(13));
        verify(accommodationRepository).findById(VALID_ACCOMMODATION_ID);
        verify(accommodationRepository).save(accommodation);
        verifyNoMoreInteractions(accommodationRepository);
        availability.removeIf(timeSlot -> timeSlot.getId().equals(2L));
        availability.add(new TimeSlot(null, referenceDate.plusDays(10), referenceDate.plusDays(13), 300, true));
        availability.add(new TimeSlot(null, referenceDate.plusDays(13), referenceDate.plusDays(15), 300, false));
        availability.sort((item1, item2) -> {
            return Math.toIntExact(item1.getStartDate().toEpochDay() - item2.getStartDate().toEpochDay());
        });
        copyTimeslots.sort((item1, item2) -> {
            return Math.toIntExact(item1.getStartDate().toEpochDay() - item2.getStartDate().toEpochDay());
        });
        for (int i =0; i<availability.size(); i++){
            assertThat(availability.get(i)).usingRecursiveComparison().ignoringFields("id").isEqualTo(copyTimeslots.get(i));
        }
    }

    @Test
    public void reserveTimeSlot_ShouldReserveEndPartOfOneTimeSlot(){
        Accommodation accommodation = new Accommodation();
        List<TimeSlot> copyTimeslots = deepCopyUsingSerialization(availability);
        accommodation.setAvailability(copyTimeslots);
        when(accommodationRepository.findById(VALID_ACCOMMODATION_ID)).thenReturn(Optional.of(accommodation));
        accommodationService.reserveTimeslot(VALID_ACCOMMODATION_ID, referenceDate.plusDays(13), referenceDate.plusDays(15));
        verify(accommodationRepository).findById(VALID_ACCOMMODATION_ID);
        verify(accommodationRepository).save(accommodation);
        verifyNoMoreInteractions(accommodationRepository);
        availability.removeIf(timeSlot -> timeSlot.getId().equals(2L));
        availability.add(new TimeSlot(null, referenceDate.plusDays(10), referenceDate.plusDays(13), 300, false));
        availability.add(new TimeSlot(null, referenceDate.plusDays(13), referenceDate.plusDays(15), 300, true));
        availability.sort((item1, item2) -> {
            return Math.toIntExact(item1.getStartDate().toEpochDay() - item2.getStartDate().toEpochDay());
        });
        copyTimeslots.sort((item1, item2) -> {
            return Math.toIntExact(item1.getStartDate().toEpochDay() - item2.getStartDate().toEpochDay());
        });
        for (int i =0; i<availability.size(); i++){
            assertThat(availability.get(i)).usingRecursiveComparison().ignoringFields("id").isEqualTo(copyTimeslots.get(i));
        }
    }
    @Test
    public void reserveTimeSlot_ShouldReserveTwoTimeSlots(){
        Accommodation accommodation = new Accommodation();
        List<TimeSlot> copyTimeslots = deepCopyUsingSerialization(availability);
        accommodation.setAvailability(copyTimeslots);
        when(accommodationRepository.findById(VALID_ACCOMMODATION_ID)).thenReturn(Optional.of(accommodation));
        accommodationService.reserveTimeslot(VALID_ACCOMMODATION_ID, referenceDate.plusDays(10), referenceDate.plusDays(20));
        verify(accommodationRepository).findById(VALID_ACCOMMODATION_ID);
        verify(accommodationRepository).save(accommodation);
        verifyNoMoreInteractions(accommodationRepository);
        availability.removeIf(timeSlot -> timeSlot.getId().equals(2L));
        availability.removeIf(timeSlot -> timeSlot.getId().equals(3L));
        availability.add(new TimeSlot(null, referenceDate.plusDays(10), referenceDate.plusDays(15), 300, true));
        availability.add(new TimeSlot(null, referenceDate.plusDays(15), referenceDate.plusDays(20), 400, true));
        availability.sort((item1, item2) -> {
            return Math.toIntExact(item1.getStartDate().toEpochDay() - item2.getStartDate().toEpochDay());
        });
        copyTimeslots.sort((item1, item2) -> {
            return Math.toIntExact(item1.getStartDate().toEpochDay() - item2.getStartDate().toEpochDay());
        });
        for (int i =0; i<availability.size(); i++){
            assertThat(availability.get(i)).usingRecursiveComparison().ignoringFields("id").isEqualTo(copyTimeslots.get(i));
        }
    }

    @Test
    public void findModelById_ShouldThrowException_IdDoesNotExist(){
        when(accommodationRepository.findById(INVALID_ACCOMMODATION_ID)).thenReturn(Optional.empty());

        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class,
                () -> accommodationService.findModelById(INVALID_ACCOMMODATION_ID));
        assertEquals("Element with given ID doesn't exist!", exception.getMessage());

        verify(accommodationRepository).findById(INVALID_ACCOMMODATION_ID);
        verifyNoMoreInteractions(accommodationRepository);
    }

    @Test
    public void findModelById_ShouldReturnAccommodation_ValidID(){
        Accommodation accommodation = new Accommodation();
        when(accommodationRepository.findById(VALID_ACCOMMODATION_ID)).thenReturn(Optional.of(accommodation));

        Accommodation result = accommodationService.findModelById(VALID_ACCOMMODATION_ID);
        assertEquals(accommodation, result);

        verify(accommodationRepository).findById(VALID_ACCOMMODATION_ID);
        verifyNoMoreInteractions(accommodationRepository);
    }
}
