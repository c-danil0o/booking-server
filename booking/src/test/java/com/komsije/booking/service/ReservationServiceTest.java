package com.komsije.booking.service;

import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.exceptions.PendingReservationException;
import com.komsije.booking.exceptions.ReservationAlreadyExistsException;
import com.komsije.booking.mapper.ReservationMapper;
import com.komsije.booking.model.*;
import com.komsije.booking.repository.ReservationRepository;
import com.komsije.booking.service.interfaces.AccommodationService;
import com.komsije.booking.service.interfaces.AccountService;
import com.komsije.booking.service.interfaces.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ReservationServiceTest {

    private final Long VALID_RESERVATION_ID = 20L;
    private final Long VALID_ACCOMMODATION_ID = 10L;

    @Autowired
    private ReservationServiceImpl reservationService;

    @MockBean
    private ReservationRepository reservationRepository;
    @MockBean
    private ReservationMapper reservationMapper;
    @MockBean
    private AccommodationService accommodationService;
    @MockBean
    private AccountService accountService;
    @MockBean
    private NotificationService notificationService;
    @Autowired
    private TaskScheduler taskScheduler;
//    @InjectMocks
//    private ReservationServiceImpl reservationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveNewReservation_ShouldThrowException_ReservationAlreadyExist(){
        ReservationDto badReservationDto = new ReservationDto(VALID_RESERVATION_ID, LocalDate.now().plusDays(5), LocalDate.now(), 3, 300, ReservationStatus.Pending, VALID_ACCOMMODATION_ID, 6L, 1L, 3);
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
    public void testSaveNewReservation_ShouldCreatePendingAndSendNotification_NewReservationNoApproved(){
        Account host = new Account();
        host.setSettings(new HashSet<>(Arrays.asList(Settings.RESERVATION_REQUEST_NOTIFICATION)));
        Account guest = new Account();
        guest.setEmail("example@12.com");


        ReservationDto reservationDto = new ReservationDto(VALID_RESERVATION_ID, LocalDate.now().plusDays(5), LocalDate.now(), 3, 300, ReservationStatus.Pending, VALID_ACCOMMODATION_ID, 6L, 1L, 3);
        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation = new Reservation(VALID_RESERVATION_ID, LocalDate.now().plusDays(5), null, 3, 3, 300, 1L, 6L, null, ReservationStatus.Pending);
        Accommodation accommodation = new Accommodation();
        accommodation.setAutoApproval(false);
        ReservationDto resDto = new ReservationDto();
        when(reservationRepository.getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId())).thenReturn(reservationList);
        when(reservationMapper.fromDto(reservationDto)).thenReturn(reservation);
        when(accommodationService.findModelById(reservationDto.getAccommodationId())).thenReturn(accommodation);
        when(reservationRepository.save(reservation)).thenReturn(null);
        when(reservationMapper.toDto(reservation)).thenReturn(resDto);
        when(accountService.findModelById(1L)).thenReturn(host);
        when(accountService.findModelById(6L)).thenReturn(guest);

        ReservationDto result = reservationService.saveNewReservation(reservationDto);
        assertEquals(reservation.getReservationStatus(), ReservationStatus.Pending);

        verify(reservationRepository).getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId());
        verify(accommodationService).findModelById(reservationDto.getAccommodationId());
        verify(reservationRepository, times(2)).save(reservation);
        verify(reservationMapper).fromDto(reservationDto);
        verify(reservationMapper).toDto(reservation);
        verify(accountService).findModelById(1L);
        verify(notificationService).saveAndSendNotification(any(Notification.class));
        verifyNoMoreInteractions(accommodationService);
    }

    @Test
    public void testSaveNewReservation_ShouldCreatePendingAndNotNotification_NewReservationNoApproved(){
        Account host = new Account();
        host.setSettings(new HashSet<>(Arrays.asList(Settings.RESERVATION_RESPONSE_NOTIFICATION)));
        Account guest = new Account();
        guest.setEmail("example@12.com");


        ReservationDto reservationDto = new ReservationDto(VALID_RESERVATION_ID, LocalDate.now().plusDays(5), LocalDate.now(), 3, 300, ReservationStatus.Pending, VALID_ACCOMMODATION_ID, 6L, 1L, 3);
        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation = new Reservation(VALID_RESERVATION_ID, LocalDate.now().plusDays(5), null, 3, 3, 300, 1L, 6L, null, ReservationStatus.Pending);
        Accommodation accommodation = new Accommodation();
        accommodation.setAutoApproval(false);
        ReservationDto resDto = new ReservationDto();
        when(reservationRepository.getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId())).thenReturn(reservationList);
        when(reservationMapper.fromDto(reservationDto)).thenReturn(reservation);
        when(accommodationService.findModelById(reservationDto.getAccommodationId())).thenReturn(accommodation);
        when(reservationRepository.save(reservation)).thenReturn(null);
        when(reservationMapper.toDto(reservation)).thenReturn(resDto);
        when(accountService.findModelById(1L)).thenReturn(host);
        when(accountService.findModelById(6L)).thenReturn(guest);

        ReservationDto result = reservationService.saveNewReservation(reservationDto);
        assertEquals(reservation.getReservationStatus(), ReservationStatus.Pending);

        verify(reservationRepository).getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId());
        verify(accommodationService).findModelById(reservationDto.getAccommodationId());
        verify(reservationRepository, times(2)).save(reservation);
        verify(reservationMapper).fromDto(reservationDto);
        verify(reservationMapper).toDto(reservation);
        verify(accountService).findModelById(1L);
        verifyNoMoreInteractions(accountService);
        verifyNoInteractions(notificationService);
        verifyNoMoreInteractions(accommodationService);
    }

    @Test
    public void testSaveNewReservation_ShouldCreateApprovedAndSendNotification_AutoApprovingAccommodation(){
        Account guest = new Account();
        guest.setSettings(new HashSet<>(Arrays.asList(Settings.RESERVATION_RESPONSE_NOTIFICATION)));
        Account host = new Account();
        host.setEmail("example@12.com");


        ReservationDto reservationDto = new ReservationDto(VALID_RESERVATION_ID, LocalDate.now().plusDays(5), LocalDate.now(), 3, 300, ReservationStatus.Pending, VALID_ACCOMMODATION_ID, 6L, 1L, 3);
        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation = new Reservation(VALID_RESERVATION_ID, LocalDate.now().plusDays(5), null, 3, 3, 300, 1L, 6L, null, ReservationStatus.Pending);
        Accommodation accommodation = new Accommodation();
        accommodation.setAutoApproval(true);
        ReservationDto resDto = new ReservationDto();
        when(reservationRepository.getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId())).thenReturn(reservationList);
        when(reservationMapper.fromDto(reservationDto)).thenReturn(reservation);
        when(accommodationService.findModelById(reservationDto.getAccommodationId())).thenReturn(accommodation);
        when(reservationRepository.save(reservation)).thenReturn(null);
        when(reservationMapper.toDto(reservation)).thenReturn(resDto);
        when(accountService.findModelById(6L)).thenReturn(guest);
        when(accountService.findModelById(1L)).thenReturn(host);
//        doNothing().when(accommodationService).reserveTimeslot(null, reservation.getStartDate(), reservation.getStartDate().plusDays(reservation.getDays()));

        ReservationDto result = reservationService.saveNewReservation(reservationDto);
        assertEquals(reservation.getReservationStatus(), ReservationStatus.Approved);

        verify(reservationRepository).getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId());
        verify(accommodationService).findModelById(reservationDto.getAccommodationId());
        verify(reservationMapper).fromDto(reservationDto);
        verify(reservationMapper).toDto(reservation);
        verify(reservationRepository, times(2)).save(reservation);
        verify(notificationService).saveAndSendNotification(any(Notification.class));
        verifyNoMoreInteractions(notificationService);

//        verify(accommodationService).reserveTimeslot(null, reservation.getStartDate(), reservation.getStartDate().plusDays(reservation.getDays()));
    }

    @Test
    public void testSaveNewReservation_ShouldCreateApprovedAndNotNotification_AutoApprovingAccommodation(){
        Account guest = new Account();
        guest.setSettings(new HashSet<>());
        Account host = new Account();
        host.setEmail("example@12.com");


        ReservationDto reservationDto = new ReservationDto(VALID_RESERVATION_ID, LocalDate.now().plusDays(5), LocalDate.now(), 3, 300, ReservationStatus.Pending, VALID_ACCOMMODATION_ID, 6L, 1L, 3);
        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation = new Reservation(VALID_RESERVATION_ID, LocalDate.now().plusDays(5), null, 3, 3, 300, 1L, 6L, null, ReservationStatus.Pending);
        Accommodation accommodation = new Accommodation();
        accommodation.setAutoApproval(true);
        ReservationDto resDto = new ReservationDto();
        when(reservationRepository.getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId())).thenReturn(reservationList);
        when(reservationMapper.fromDto(reservationDto)).thenReturn(reservation);
        when(accommodationService.findModelById(reservationDto.getAccommodationId())).thenReturn(accommodation);
        when(reservationRepository.save(reservation)).thenReturn(null);
        when(reservationMapper.toDto(reservation)).thenReturn(resDto);
        when(accountService.findModelById(6L)).thenReturn(guest);
        when(accountService.findModelById(1L)).thenReturn(host);
//        doNothing().when(accommodationService).reserveTimeslot(null, reservation.getStartDate(), reservation.getStartDate().plusDays(reservation.getDays()));

        ReservationDto result = reservationService.saveNewReservation(reservationDto);
        assertEquals(reservation.getReservationStatus(), ReservationStatus.Approved);

        verify(reservationRepository).getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId());
        verify(accommodationService).findModelById(reservationDto.getAccommodationId());
        verify(reservationMapper).fromDto(reservationDto);
        verify(reservationMapper).toDto(reservation);
        verify(reservationRepository, times(2)).save(reservation);
        verifyNoInteractions(notificationService);

//        verify(accommodationService).reserveTimeslot(null, reservation.getStartDate(), reservation.getStartDate().plusDays(reservation.getDays()));
    }

    @Test
    public void testSaveNewReservation_ShouldCreateActiveAndSendNotification_AutoApprovingAccommodation() throws InterruptedException {
        Account guest = new Account();
        guest.setSettings(new HashSet<>(Arrays.asList(Settings.RESERVATION_RESPONSE_NOTIFICATION)));
        Account host = new Account();
        host.setEmail("example@12.com");


        ReservationDto reservationDto = new ReservationDto(VALID_RESERVATION_ID, LocalDate.now(), LocalDate.now(), 3, 300, ReservationStatus.Pending, VALID_ACCOMMODATION_ID, 6L, 1L, 3);
        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation = new Reservation(VALID_RESERVATION_ID, LocalDate.now(), null, 3, 3, 300, 1L, 6L, null, ReservationStatus.Pending);
        Accommodation accommodation = new Accommodation();
        accommodation.setAutoApproval(true);
        ReservationDto resDto = new ReservationDto();
        when(reservationRepository.getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId())).thenReturn(reservationList);
        when(reservationMapper.fromDto(reservationDto)).thenReturn(reservation);
        when(accommodationService.findModelById(reservationDto.getAccommodationId())).thenReturn(accommodation);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(reservationMapper.toDto(reservation)).thenReturn(resDto);
        when(accountService.findModelById(6L)).thenReturn(guest);
        when(accountService.findModelById(1L)).thenReturn(host);

        ReservationDto result = reservationService.saveNewReservation(reservationDto);
        Thread.sleep(500);
        assertEquals(reservation.getReservationStatus(), ReservationStatus.Active);

        verify(reservationRepository).getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId());
        verify(accommodationService).findModelById(reservationDto.getAccommodationId());
        verify(reservationMapper).fromDto(reservationDto);
        verify(reservationMapper).toDto(reservation);
        verify(notificationService).saveAndSendNotification(any(Notification.class));
        verifyNoMoreInteractions(notificationService);
    }

    @Test
    public void testSaveNewReservation_ShouldCreateDoneAndSendNotification_AutoApprovingAccommodation() throws InterruptedException {
        Account guest = new Account();
        guest.setSettings(new HashSet<>(Arrays.asList(Settings.RESERVATION_RESPONSE_NOTIFICATION)));
        Account host = new Account();
        host.setEmail("example@12.com");

        ReservationDto reservationDto = new ReservationDto(VALID_RESERVATION_ID, LocalDate.now().minusDays(3), LocalDate.now(), 3, 300, ReservationStatus.Pending, VALID_ACCOMMODATION_ID, 6L, 1L, 3);
        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation = new Reservation(VALID_RESERVATION_ID, LocalDate.now().minusDays(3), null, 2, 3, 300, 1L, 6L, null, ReservationStatus.Pending);
        Accommodation accommodation = new Accommodation();
        accommodation.setAutoApproval(true);
        ReservationDto resDto = new ReservationDto();
        when(reservationRepository.getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId())).thenReturn(reservationList);
        when(reservationMapper.fromDto(reservationDto)).thenReturn(reservation);
        when(accommodationService.findModelById(reservationDto.getAccommodationId())).thenReturn(accommodation);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(reservationMapper.toDto(reservation)).thenReturn(resDto);
        when(accountService.findModelById(6L)).thenReturn(guest);
        when(accountService.findModelById(1L)).thenReturn(host);

        ReservationDto result = reservationService.saveNewReservation(reservationDto);
        Thread.sleep(500);
        assertEquals(reservation.getReservationStatus(), ReservationStatus.Done);

        verify(reservationRepository).getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId());
        verify(accommodationService).findModelById(reservationDto.getAccommodationId());
        verify(reservationMapper).fromDto(reservationDto);
        verify(reservationMapper).toDto(reservation);
        verify(notificationService).saveAndSendNotification(any(Notification.class));
        verifyNoMoreInteractions(notificationService);
    }

    @Test
    public void testSaveNewReservation_ShouldCreateDeniedAndSendHostNotification_AutoApprovingAccommodation() throws InterruptedException {
        Account host = new Account();
        host.setSettings(new HashSet<>(Arrays.asList(Settings.RESERVATION_REQUEST_NOTIFICATION)));
        Account guest = new Account();
        guest.setEmail("example@12.com");

        ReservationDto reservationDto = new ReservationDto(VALID_RESERVATION_ID, LocalDate.now(), LocalDate.now(), 3, 300, ReservationStatus.Pending, VALID_ACCOMMODATION_ID, 6L, 1L, 3);
        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation = new Reservation(VALID_RESERVATION_ID, LocalDate.now(), null, 3, 3, 300, 1L, 6L, null, ReservationStatus.Pending);
        Accommodation accommodation = new Accommodation();
        accommodation.setAutoApproval(false);
        ReservationDto resDto = new ReservationDto();
        when(reservationRepository.getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId())).thenReturn(reservationList);
        when(reservationMapper.fromDto(reservationDto)).thenReturn(reservation);
        when(accommodationService.findModelById(reservationDto.getAccommodationId())).thenReturn(accommodation);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(reservationMapper.toDto(reservation)).thenReturn(resDto);
        when(accountService.findModelById(6L)).thenReturn(guest);
        when(accountService.findModelById(1L)).thenReturn(host);

        ReservationDto result = reservationService.saveNewReservation(reservationDto);
        Thread.sleep(500);
        assertEquals(reservation.getReservationStatus(), ReservationStatus.Denied);

        verify(reservationRepository).getIfExists(reservationDto.getStartDate(), reservationDto.getAccommodationId(), reservationDto.getGuestId());
        verify(accommodationService).findModelById(reservationDto.getAccommodationId());
        verify(reservationMapper).fromDto(reservationDto);
        verify(reservationMapper).toDto(reservation);
        verify(notificationService).saveAndSendNotification(any(Notification.class));
        verifyNoMoreInteractions(notificationService);
    }



    @Test
    public void testAcceptReservationRequest_ShouldThrowException_ReservationNotPending() {

        Reservation reservation = new Reservation(VALID_RESERVATION_ID, LocalDate.now().plusDays(5), null, 3, 3, 300, 1L, 6L, null, ReservationStatus.Approved);
        when(reservationRepository.findById(VALID_RESERVATION_ID)).thenReturn(Optional.of(reservation));

        PendingReservationException exception = assertThrows(PendingReservationException.class, () -> reservationService.acceptReservationRequest(VALID_RESERVATION_ID));
        assertEquals("Reservation is not in pending state!", exception.getMessage());

        verify(reservationRepository).findById(VALID_RESERVATION_ID);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    public void testAcceptReservationRequest_ShouldSetStatusToApprovedSendGuestNotificationAndReturnTrue_StartDateInTwoDays() throws InterruptedException {
        Account guest = new Account();
        guest.setSettings(new HashSet<>(Arrays.asList(Settings.RESERVATION_RESPONSE_NOTIFICATION)));
        Account host = new Account();
        host.setEmail("example@12.com");


        Accommodation accommodation = new Accommodation();
        LocalDate startDate = LocalDate.now().plusDays(2);
        LocalDate endDate = startDate.plusDays(3);
        accommodation.setId(VALID_ACCOMMODATION_ID);
        Reservation reservation = new Reservation(VALID_RESERVATION_ID, startDate, LocalDate.now(), 3, 3, 300, 1L, 6L, accommodation, ReservationStatus.Pending);
        when(reservationRepository.findById(VALID_RESERVATION_ID)).thenReturn(Optional.of(reservation));
        when(accountService.findModelById(6L)).thenReturn(guest);
        when(accountService.findModelById(1L)).thenReturn(host);
        boolean result = reservationService.acceptReservationRequest(VALID_RESERVATION_ID);

        assertTrue(result);
        assertEquals(ReservationStatus.Approved, reservation.getReservationStatus());

        verify(reservationRepository).findById(VALID_RESERVATION_ID);
        verify(reservationRepository,atLeast(1)).save(reservation);
        verify(reservationRepository).findPendingByAccommodationId(accommodation.getId());
        verifyNoMoreInteractions(reservationRepository);
        verify(notificationService).saveAndSendNotification(any(Notification.class));
        verifyNoMoreInteractions(notificationService);


    }

    @Test
    public void testAcceptReservationRequest_ShouldSetStatusToActiveSendGuestNotificationAndReturnTrue_StartDateNow() throws InterruptedException {
        Account guest = new Account();
        guest.setSettings(new HashSet<>(Arrays.asList(Settings.RESERVATION_RESPONSE_NOTIFICATION)));
        Account host = new Account();
        host.setEmail("example@12.com");

        Accommodation accommodation = new Accommodation();
        LocalDate startDate = LocalDate.now();
        accommodation.setId(VALID_ACCOMMODATION_ID);
        Reservation reservation = new Reservation(VALID_RESERVATION_ID, startDate, LocalDate.now(), 3, 3, 300, 1L, 6L, accommodation, ReservationStatus.Pending);
        when(reservationRepository.findById(VALID_RESERVATION_ID)).thenReturn(Optional.of(reservation));
        when(accountService.findModelById(6L)).thenReturn(guest);
        when(accountService.findModelById(1L)).thenReturn(host);
        boolean result = reservationService.acceptReservationRequest(VALID_RESERVATION_ID);

        assertTrue(result);
        Thread.sleep(500);
        assertEquals(ReservationStatus.Active, reservation.getReservationStatus());

        verify(reservationRepository,atLeast(1)).save(reservation);
        verify(reservationRepository).findById(VALID_RESERVATION_ID);
        verify(reservationRepository).findPendingByAccommodationId(accommodation.getId());
        verify(notificationService).saveAndSendNotification(any(Notification.class));
        verifyNoMoreInteractions(notificationService);

    }

    @Test
    public void testAcceptReservationRequest_ShouldSetStatusToDoneSendNotificationAndReturnTrue_EndDatePast() throws InterruptedException {
        Account guest = new Account();
        guest.setSettings(new HashSet<>(Arrays.asList(Settings.RESERVATION_RESPONSE_NOTIFICATION)));
        Account host = new Account();
        host.setEmail("example@12.com");

        Accommodation accommodation = new Accommodation();
        LocalDate startDate = LocalDate.now().minusDays(10);
        accommodation.setId(VALID_ACCOMMODATION_ID);
        Reservation reservation = new Reservation(VALID_RESERVATION_ID, startDate, LocalDate.now(), 3, 3, 300, 1L, 6L, accommodation, ReservationStatus.Pending);
        when(reservationRepository.findById(VALID_RESERVATION_ID)).thenReturn(Optional.of(reservation));
        when(accountService.findModelById(6L)).thenReturn(guest);
        when(accountService.findModelById(1L)).thenReturn(host);
        boolean result = reservationService.acceptReservationRequest(VALID_RESERVATION_ID);

        assertTrue(result);
        Thread.sleep(500);

        assertEquals(ReservationStatus.Done, reservation.getReservationStatus());

        verify(reservationRepository, atLeast(1)).save(reservation);
        verify(reservationRepository).findById(VALID_RESERVATION_ID);
        verify(reservationRepository).findPendingByAccommodationId(accommodation.getId());
        verify(notificationService).saveAndSendNotification(any(Notification.class));
        verifyNoMoreInteractions(notificationService);

    }

    @ParameterizedTest
    @MethodSource("provideDateRanges")
    public void testDenyOverlappingRequests_ShouldDenyThree(int start, int end, int overlap) {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(VALID_ACCOMMODATION_ID);
        // reservation is from 10 to 20
        // input params are start, end
        LocalDate date = LocalDate.now();
        Reservation reservation = new Reservation(null, date.plusDays(10), LocalDate.now(), 10, 3, 300, 1L, 6L, accommodation, ReservationStatus.Approved);
        Reservation reservation2 = new Reservation(null, date.plusDays(start), LocalDate.now(), end - start, 3, 300, 1L, 6L, accommodation, ReservationStatus.Pending);
        when(reservationRepository.findPendingByAccommodationId(accommodation.getId())).thenReturn(List.of(reservation2));
        reservationService.denyOverlappingRequests(reservation.getStartDate(), reservation.getStartDate().plusDays(reservation.getDays()), accommodation.getId());
        verify(reservationRepository).findPendingByAccommodationId(accommodation.getId());
        if (overlap == 1) {
            assertEquals(ReservationStatus.Denied, reservation2.getReservationStatus());
            verify(reservationRepository).save(reservation2);
        } else {
            verifyNoMoreInteractions(reservationRepository);
        }
    }

    private static Collection provideDateRanges() {
        return Arrays.asList(
                new Integer[][]{
                        {9, 15, 1}, {8, 11, 1}, {12, 21, 1}, {12, 18, 1}, {3, 4, 0}, {21, 22, 0}, {10, 20, 1}
                }
        );
    }



    @Test
    public void denyReservationRequest_ShouldThrowPendingResException_ReservationNotPending(){
        Reservation reservation = new Reservation(null, LocalDate.now(), LocalDate.now(), 10, 3, 300, 1L, 6L, null, ReservationStatus.Approved);
        when(reservationRepository.findById(VALID_RESERVATION_ID)).thenReturn(Optional.of(reservation));

        PendingReservationException exception = assertThrows(PendingReservationException.class, () -> reservationService.denyReservationRequest(VALID_RESERVATION_ID));
        assertEquals("Reservation is not in pending state!", exception.getMessage());
        assertEquals(ReservationStatus.Approved, reservation.getReservationStatus());

        verify(reservationRepository).findById(VALID_RESERVATION_ID);
        verifyNoMoreInteractions(reservationRepository);

    }

    @Test
    public void denyReservationRequest_ShouldReturnTrue_ReservationPending(){
        Account guest = new Account();
        guest.setSettings(new HashSet<>(Arrays.asList(Settings.RESERVATION_RESPONSE_NOTIFICATION)));
        Account host = new Account();
        host.setEmail("example@12.com");

        Reservation reservation = new Reservation(null, LocalDate.now(), LocalDate.now(), 10, 3, 300, 1L, 6L, null, ReservationStatus.Pending);
        when(reservationRepository.findById(VALID_RESERVATION_ID)).thenReturn(Optional.of(reservation));
        when(accountService.findModelById(6L)).thenReturn(guest);
        when(accountService.findModelById(1L)).thenReturn(host);

        assertTrue(reservationService.denyReservationRequest(VALID_RESERVATION_ID));
        assertEquals(ReservationStatus.Denied, reservation.getReservationStatus());

        verify(reservationRepository).save(reservation);
        verify(reservationRepository).findById(VALID_RESERVATION_ID);
        verifyNoMoreInteractions(reservationRepository);
        verify(notificationService).saveAndSendNotification(any(Notification.class));
        verifyNoMoreInteractions(notificationService);
    }
}
