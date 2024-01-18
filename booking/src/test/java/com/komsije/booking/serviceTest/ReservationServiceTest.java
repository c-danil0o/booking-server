package com.komsije.booking.serviceTest;

import com.komsije.booking.dto.NewReservationDto;
import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.exceptions.ReservationAlreadyExistsException;
import com.komsije.booking.mapper.ReservationMapper;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.ReservationStatus;
import com.komsije.booking.repository.ReservationRepository;
import com.komsije.booking.service.ReservationServiceImpl;
import com.komsije.booking.service.interfaces.AccommodationService;
import com.komsije.booking.service.interfaces.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.TaskScheduler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @Mock
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
        when(reservationRepository.findForNewReservation(badReservationDto.getStartDate(), badReservationDto.getDays(), badReservationDto.getAccommodationId(), badReservationDto.getGuestId())).thenReturn(reservationList);

        ReservationAlreadyExistsException exception = assertThrows(ReservationAlreadyExistsException.class,
                () -> reservationService.saveNewReservation(badReservationDto));
        assertEquals("You already made reservation for this dates for this accommodation", exception.getMessage());

        verify(reservationRepository).findForNewReservation(badReservationDto.getStartDate(), badReservationDto.getDays(), badReservationDto.getAccommodationId(), badReservationDto.getGuestId());
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
        when(reservationRepository.findForNewReservation(reservationDto.getStartDate(), reservationDto.getDays(), reservationDto.getAccommodationId(), reservationDto.getGuestId())).thenReturn(reservationList);
        when(reservationMapper.fromNewDto(reservationDto)).thenReturn(reservation);
        when(accommodationService.findModelById(reservationDto.getAccommodationId())).thenReturn(accommodation);
        doNothing().when(reservationRepository).save(reservation);
        when(reservationMapper.toDto(reservation)).thenReturn(resDto);

    }





}
