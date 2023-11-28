package com.komsije.booking.service;

import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.mapper.GuestMapper;
import com.komsije.booking.model.Guest;
import com.komsije.booking.repository.GuestRepository;
import com.komsije.booking.service.interfaces.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {
    @Autowired
    private GuestMapper mapper;
    private final GuestRepository guestRepository;

    @Autowired
    public GuestServiceImpl(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public GuestDto findById(Long id) {
        return mapper.toDto( guestRepository.findById(id).orElseGet(null));
    }

    public List<GuestDto> findAll() {
        return mapper.toDto(guestRepository.findAll());
    }

    public GuestDto save(GuestDto guestDto) {
        guestRepository.save(mapper.fromDto(guestDto));
        return guestDto;
    }

    @Override
    public GuestDto update(GuestDto guestDto) {
        Guest guest = guestRepository.findById(guestDto.getId()).orElseGet(null);
        if (guest == null){
            return null;
        }
        mapper.update(guest, guestDto);
        guestRepository.save(guest);
        return guestDto;
    }

    public void delete(Long id) {
        guestRepository.deleteById(id);
    }
}
