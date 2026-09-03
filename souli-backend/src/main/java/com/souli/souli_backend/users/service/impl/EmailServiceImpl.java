package com.souli.souli_backend.users.service.impl;

import com.souli.souli_backend.users.domain.User;
import com.souli.souli_backend.users.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendRegistrationConfirmation(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(user.getEmail());
        message.setSubject("Confirmation de votre inscription à Souli");
        message.setText("Bonjour " + user.getFirstName() + ",\n\n"
                + "Votre compte Souli a bien été créé.\n\n"
                + "À bientôt,\nL'équipe Souli");

        mailSender.send(message);
    }
}
