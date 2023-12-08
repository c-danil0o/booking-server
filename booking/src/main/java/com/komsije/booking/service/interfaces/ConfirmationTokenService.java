package com.komsije.booking.service.interfaces;

import com.komsije.booking.model.ConfirmationToken;
import com.komsije.booking.repository.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationTokenService {

    public void saveConfirmationToken(ConfirmationToken token);

    public Optional<ConfirmationToken> getToken(String token);

    public int setConfirmedAt(String token);
}
