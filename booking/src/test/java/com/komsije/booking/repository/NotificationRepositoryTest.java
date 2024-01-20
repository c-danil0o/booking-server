package com.komsije.booking.repository;

import com.komsije.booking.model.Notification;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class NotificationRepositoryTest {
    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    public void shouldSaveNotification(){
        Notification notification = new Notification(null, "message", LocalDateTime.now(), null);
        Notification savedNotification = notificationRepository.save(notification);
        assertThat(savedNotification).usingRecursiveComparison().ignoringFields("id").isEqualTo(notification);
    }
}
