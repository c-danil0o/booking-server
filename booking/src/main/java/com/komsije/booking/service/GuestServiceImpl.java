package com.komsije.booking.service;

import com.komsije.booking.dto.*;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.EmailAlreadyExistsException;
import com.komsije.booking.exceptions.HasActiveReservationsException;
import com.komsije.booking.exceptions.PendingReservationException;
import com.komsije.booking.mapper.AccommodationMapper;
import com.komsije.booking.mapper.AddressMapper;
import com.komsije.booking.mapper.GuestMapper;
import com.komsije.booking.model.*;
import com.komsije.booking.repository.AccountRepository;
import com.komsije.booking.repository.GuestRepository;
import com.komsije.booking.service.interfaces.ConfirmationTokenService;
import com.komsije.booking.service.interfaces.GuestService;
import com.komsije.booking.service.interfaces.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GuestServiceImpl implements GuestService {
    @Autowired
    private GuestMapper mapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private AccommodationMapper accommodationMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final GuestRepository guestRepository;
    private final AccountRepository accountRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final ReservationService reservationService;

    @Autowired
    public GuestServiceImpl(GuestRepository guestRepository, AccountRepository accountRepository, ConfirmationTokenService confirmationTokenService, ReservationService reservationService) {
        this.guestRepository = guestRepository;
        this.accountRepository = accountRepository;
        this.confirmationTokenService=confirmationTokenService;
        this.reservationService = reservationService;
    }

    public GuestDto findById(Long id) throws ElementNotFoundException {
        return mapper.toDto( guestRepository.findById(id).orElseThrow(()-> new ElementNotFoundException("Element with given ID doesn't exist!")));
    }

    public List<GuestDto> findAll() {
        return mapper.toDto(guestRepository.findAll());
    }

    public GuestDto save(GuestDto guestDto) {
        guestRepository.save(mapper.fromDto(guestDto));
        return guestDto;
    }

    @Override
    public GuestDto update(GuestDto guestDto) throws ElementNotFoundException {
        Guest guest = guestRepository.findById(guestDto.getId()).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        mapper.update(guest, guestDto);
        guestRepository.save(guest);
        return guestDto;
    }

    public void delete(Long id) throws ElementNotFoundException {
        if (guestRepository.existsById(id)){
            if (!reservationService.hasActiveReservations(id)){
                guestRepository.deleteById(id);
            }else{
                throw new HasActiveReservationsException("Account has active reservations and can't be deleted!");
            }
        }else{
            throw new  ElementNotFoundException("Element with given ID doesn't exist!");
        }

    }

    @Override
    public List<AccommodationDto> getFavoritesByGuestId(Long id) throws ElementNotFoundException {
        Guest guest = guestRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        return accommodationMapper.toDto(guest.getFavorites().stream().toList());
    }

    @Override
    public List<AccommodationDto> addToFavorites(Long id, AccommodationDto accommodationDto) throws ElementNotFoundException {
        Guest guest = guestRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        guest.getFavorites().add(accommodationMapper.fromDto(accommodationDto));
        guestRepository.save(guest);
        return accommodationMapper.toDto(guest.getFavorites().stream().toList());
    }

    @Override
    public String singUpUser(RegistrationDto registrationDto) {
        Account account = accountRepository.getAccountByEmail(registrationDto.getEmail());
        Long id;
        if (account==null){
            String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
            registrationDto.setPassword(encodedPassword);
            Guest guest = mapper.fromRegistrationDto(registrationDto);
            guestRepository.save(guest);
            id = guest.getId();
        }
        else if (account.isActivated() || account.isBlocked()){
            throw new EmailAlreadyExistsException("Email already exists!");
        }else
            id = account.getId();

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token,LocalDateTime.now(),LocalDateTime.now().plusHours(24),accountRepository.findById(id).orElseGet(null));

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    @Override
    public GuestDto getByEmail(String email) throws ElementNotFoundException {
        Guest guest = guestRepository.findByEmail(email);
        if (guest == null){
            throw new ElementNotFoundException("Account with given email doesn't exit!");
        }
        return mapper.toDto(guest);
    }

    @Override
    public Guest getModelByEmail(String email) throws ElementNotFoundException {
        Guest guest = guestRepository.findByEmail(email);
        if (guest == null){
            throw new ElementNotFoundException("Account with given email doesn't exit!");
        }
        return guest;
    }

    @Override
    public void increaseCancelations(Long id) throws ElementNotFoundException {
        Guest guest = guestRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        int timesCancelled =  guest.getTimesCancelled();
        guest.setTimesCancelled(timesCancelled+1);
        guestRepository.save(guest);
    }

    @Override
    public boolean cancelReservationRequest(Long id) throws ElementNotFoundException, PendingReservationException {
        ReservationDto reservation = reservationService.findById(id);
        if(reservation.getReservationStatus().equals(ReservationStatus.Pending) || reservation.getReservationStatus().equals(ReservationStatus.Approved)){
            reservation.setReservationStatus(ReservationStatus.Cancelled);
            reservationService.updateStatus(reservation.getId(),ReservationStatus.Cancelled );
        }else{
            throw new PendingReservationException("Reservation is not in pending or approved state!");
        }
//        todo: update accommodations if reservation was approved

        this.increaseCancelations(reservation.getGuest().getId());
        return true;
    }
}
