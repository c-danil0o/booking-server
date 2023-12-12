package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.RegistrationDto;
import com.komsije.booking.dto.TokenDto;

public interface RegistrationService {
    public TokenDto register(RegistrationDto registrationDto);
    public TokenDto registerAndroid(RegistrationDto registrationDto);
    public String confirmToken(String token);
}
