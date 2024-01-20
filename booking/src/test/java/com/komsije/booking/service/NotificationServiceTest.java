package com.komsije.booking.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.komsije.booking.dto.NotificationDto;
import com.komsije.booking.mapper.NotificationMapper;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.Notification;
import com.komsije.booking.repository.NotificationRepository;
import com.komsije.booking.service.interfaces.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class NotificationServiceTest {
    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;
    @MockBean
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationServiceImpl notificationService;
    @MockBean
    private NotificationMapper notificationMapper;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testSaveAndSendNotification_ShouldSaveAndSendToFirebase() throws FirebaseMessagingException {
        Account account = new Account();
        account.setId(12L);
        Notification notification = new Notification(null, "message", LocalDateTime.now(), account);
        FirebaseMessaging mock = Mockito.mock(FirebaseMessaging.class);

        NotificationDto notificationDto = new NotificationDto();
        when(notificationMapper.toDto(notification)).thenReturn(notificationDto);
        try(MockedStatic<FirebaseMessaging> dummy = Mockito.mockStatic(FirebaseMessaging.class)){
            dummy.when(FirebaseMessaging::getInstance).thenReturn(mock);
            when(mock.send(any(Message.class))).thenReturn("dummy_response");

            String response = notificationService.saveAndSendNotification(notification);
            assertEquals("dummy_response",response );

            verify(notificationRepository).save(notification);
            verify(simpMessagingTemplate).convertAndSend("/socket-publisher/" + notification.getReceiver().getId(), notificationDto);
        }



    }
}
