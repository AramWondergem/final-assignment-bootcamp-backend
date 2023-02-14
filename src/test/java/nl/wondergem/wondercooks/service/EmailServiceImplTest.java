package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.model.EmailDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    EmailServiceImpl emailService;

    EmailDetails emailDetails;

    @Test
    void sendSimpleMail() {
        //arrange
        emailDetails = new EmailDetails("wonderreclame@gmail.com", "Hello world", "Hello world");

        //act
        String result = emailService.sendSimpleMail(emailDetails);

        //assert
        assertEquals("Mail Sent Successfully...", result);
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}