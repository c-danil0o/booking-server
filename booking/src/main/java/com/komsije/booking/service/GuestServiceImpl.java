package com.komsije.booking.service;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.dto.RegistrationDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.mapper.AccommodationMapper;
import com.komsije.booking.mapper.AddressMapper;
import com.komsije.booking.mapper.GuestMapper;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.ConfirmationToken;
import com.komsije.booking.model.Guest;
import com.komsije.booking.model.Role;
import com.komsije.booking.repository.AccountRepository;
import com.komsije.booking.repository.GuestRepository;
import com.komsije.booking.service.interfaces.ConfirmationTokenService;
import com.komsije.booking.service.interfaces.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    public GuestServiceImpl(GuestRepository guestRepository, AccountRepository accountRepository,ConfirmationTokenService confirmationTokenService) {
        this.guestRepository = guestRepository;
        this.accountRepository = accountRepository;
        this.confirmationTokenService=confirmationTokenService;
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
            guestRepository.deleteById(id);
        }else{
            throw  new ElementNotFoundException("Element with given ID doesn't exist!");
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
            Guest guest = new Guest();
            guest.setEmail(registrationDto.getEmail());
            guest.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            guest.setPhone(registrationDto.getPhone());
            guest.setAddress(addressMapper.fromDto(registrationDto.getAddress()));
            guest.setFirstName(registrationDto.getFirstName());
            guest.setLastName(registrationDto.getLastName());
            guest.setTimesCancelled(0);
            guest.setRole(Role.Guest);
            guestRepository.save(guest);
            id = guest.getId();
        }
        else if (account.isActivated() || account.isBlocked()){
            throw new IllegalStateException("email already taken");
        }else
            id = account.getId();

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusHours(24));
        confirmationToken.setAccount(accountRepository.findById(id).orElseGet(null));

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }
}
