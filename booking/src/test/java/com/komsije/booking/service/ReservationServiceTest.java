package com.komsije.booking.service;

import com.komsije.booking.dto.NewReservationDto;
import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.exceptions.ReservationAlreadyExistsException;
import com.komsije.booking.mapper.ReservationMapper;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.ReservationStatus;
import com.komsije.booking.repository.ReservationRepository;
import com.komsije.booking.service.interfaces.AccommodationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;
import org.springframework.scheduling.TaskScheduler;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ReservationMapper reservationMapper;
    @Mock
    private AccommodationService accommodationService;
    @Spy
    private TaskScheduler taskScheduler;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_reservation_already_exist_exception(){
        NewReservationDto badReservationDto = new NewReservationDto(1L, LocalDate.now().plusDays(5), 3, 300, ReservationStatus.Pending, 1L, 6L, 1L, 3);
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(new Reservation());
        when(reservationRepository.getIfExists(badReservationDto.getStartDate(),  badReservationDto.getAccommodationId(), badReservationDto.getGuestId())).thenReturn(reservationList);

        ReservationAlreadyExistsException exception = assertThrows(ReservationAlreadyExistsException.class,
                () -> reservationService.saveNewReservation(badReservationDto));
        assertEquals("You already made reservation for this dates for this accommodation", exception.getMessage());

        verify(reservationRepository).getIfExists(badReservationDto.getStartDate(),  badReservationDto.getAccommodationId(), badReservationDto.getGuestId());
        verifyNoInteractions(reservationMapper);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }
//    Element with given ID doesn't exist!
    @Test
    public void test_no_auto_approval_reservation(){
        NewReservationDto reservationDto = new NewReservationDto(1L, LocalDate.now().plusDays(5), 3, 300, ReservationStatus.Pending, 1L, 6L, 1L, 3);
        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation = new Reservation(1L, LocalDate.now().plusDays(5), null, 3, 3, 300, 1L, 6L, null, ReservationStatus.Pending);
        Accommodation accommodation = new Accommodation();
        accommodation.setAutoApproval(false);
        ReservationDto resDto = new ReservationDto();
        when(reservationRepository.getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId())).thenReturn(reservationList);
        when(reservationMapper.fromNewDto(reservationDto)).thenReturn(reservation);
        when(accommodationService.findModelById(reservationDto.getAccommodationId())).thenReturn(accommodation);
        when(reservationRepository.save(reservation)).thenReturn(null);
        when(reservationMapper.toDto(reservation)).thenReturn(resDto);
        doNothing().when(accommodationService).reserveTimeslot(reservation.getId(), LocalDate.now(), LocalDate.now().plusDays(2));

        ReservationDto result = reservationService.saveNewReservation(reservationDto);
        assertEquals(reservation.getReservationStatus(), ReservationStatus.Pending);

        verify(reservationRepository).getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId());
        verify(accommodationService).findModelById(reservationDto.getAccommodationId());
        verify(reservationRepository).save(reservation);
        verify(reservationMapper).fromNewDto(reservationDto);
        verify(reservationMapper).toDto(reservation);
        verifyNoMoreInteractions(accommodationService);
    }

    @Test
    public void test_auto_approval_reservation(){
        NewReservationDto reservationDto = new NewReservationDto(1L, LocalDate.now().plusDays(5), 3, 300, ReservationStatus.Pending, 1L, 6L, 1L, 3);
        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation = new Reservation(1L, LocalDate.now().plusDays(5), null, 3, 3, 300, 1L, 6L, null, ReservationStatus.Pending);
        Accommodation accommodation = new Accommodation();
        accommodation.setAutoApproval(true);
        ReservationDto resDto = new ReservationDto();
        when(reservationRepository.getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId())).thenReturn(reservationList);
        when(reservationMapper.fromNewDto(reservationDto)).thenReturn(reservation);
        when(accommodationService.findModelById(reservationDto.getAccommodationId())).thenReturn(accommodation);
        when(reservationRepository.save(reservation)).thenReturn(null);
        when(reservationMapper.toDto(reservation)).thenReturn(resDto);
        doNothing().when(accommodationService).reserveTimeslot(reservation.getId(), LocalDate.now(), LocalDate.now().plusDays(2));
//        doNothing().when(accommodationService).reserveTimeslot(anyLong(), LocalDate.now(), LocalDate.now().plusDays(2));




        ReservationDto result = reservationService.saveNewReservation(reservationDto);
        assertEquals(reservation.getReservationStatus(), ReservationStatus.Approved);

        verify(reservationRepository).getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId());
        verify(accommodationService).findModelById(reservationDto.getAccommodationId());
        verify(reservationMapper).fromNewDto(reservationDto);
        verify(reservationMapper).toDto(reservation);
        verify(reservationRepository).save(reservation);
    }





}
