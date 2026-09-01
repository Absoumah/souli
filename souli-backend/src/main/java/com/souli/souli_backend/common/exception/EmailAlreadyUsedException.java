package com.souli.souli_backend.common.exception;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException() {
        super("Cet email est déjà utilisé.");
    }
}
