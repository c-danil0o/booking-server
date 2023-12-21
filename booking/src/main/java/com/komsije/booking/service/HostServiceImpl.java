package com.komsije.booking.service;

import com.komsije.booking.dto.HostDto;
import com.komsije.booking.dto.RegistrationDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.EmailAlreadyExistsException;
import com.komsije.booking.exceptions.HasActiveReservationsException;
import com.komsije.booking.mapper.AddressMapper;
import com.komsije.booking.mapper.HostMapper;
import com.komsije.booking.model.*;
import com.komsije.booking.repository.AccountRepository;
import com.komsije.booking.repository.HostRepository;
import com.komsije.booking.service.interfaces.ConfirmationTokenService;
import com.komsije.booking.service.interfaces.HostService;
import com.komsije.booking.service.interfaces.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class HostServiceImpl implements HostService {
    @Autowired
    private HostMapper mapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final HostRepository hostRepository;
    private final AccountRepository accountRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final ReservationService reservationService;


    @Autowired
    public HostServiceImpl(HostRepository hostRepository, AccountRepository accountRepository, ConfirmationTokenService confirmationTokenService, ReservationService reservationService) {
        this.hostRepository = hostRepository;
        this.accountRepository = accountRepository;
        this.confirmationTokenService = confirmationTokenService;
        this.reservationService = reservationService;
    }

    public HostDto findById(Long id) throws ElementNotFoundException {
        return mapper.toDto(hostRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!")));
    }

    public List<HostDto> findAll() {
        return mapper.toDto(hostRepository.findAll());
    }

    public HostDto save(HostDto hostDto) {
        hostRepository.save(mapper.fromDto(hostDto));
        return hostDto;
    }

    @Override
    public HostDto update(HostDto hostDto) throws ElementNotFoundException {
        Host host = hostRepository.findById(hostDto.getId()).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        mapper.update(host, hostDto);
        hostRepository.save(host);
        return hostDto;
    }
    public void delete(Long id) throws ElementNotFoundException {
        if (hostRepository.existsById(id)){
            if (!reservationService.hasHostActiveReservations(id)){
                hostRepository.deleteById(id);
            }else{
                throw new HasActiveReservationsException("Account has active reservations and can't be deleted!");
            }
        }else{
            throw new  ElementNotFoundException("Element with given ID doesn't exist!");
        }

    }


    @Override
    public String singUpUser(RegistrationDto registrationDto) {
        Account account = accountRepository.getAccountByEmail(registrationDto.getEmail());
        Long id;
        if (account==null){
            String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
            registrationDto.setPassword(encodedPassword);
            Host host = mapper.fromRegistrationDto(registrationDto);
            hostRepository.save(host);
            id = host.getId();
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
    public HostDto getByEmail(String email) throws ElementNotFoundException {
        Host host = hostRepository.findByEmail(email);
        if (host == null){
            throw new ElementNotFoundException("Account with given email doesn't exit!");
        }
        return mapper.toDto(host);
    }

    @Override
    public Host getModelByEmail(String email) throws ElementNotFoundException {
        Host host = hostRepository.findByEmail(email);
        if (host == null){
            throw new ElementNotFoundException("Account with given email doesn't exit!");
        }
        return host;
    }
}
