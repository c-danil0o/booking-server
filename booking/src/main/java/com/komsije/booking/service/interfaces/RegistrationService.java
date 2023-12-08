package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.RegistrationDto;

public interface RegistrationService {
    public String register(RegistrationDto registrationDto);
    public String confirmToken(String token);
}
