package com.souli.souli_backend.users.service;

import com.souli.souli_backend.users.domain.User;

public interface EmailService {
    void sendRegistrationConfirmation(User user);
}
