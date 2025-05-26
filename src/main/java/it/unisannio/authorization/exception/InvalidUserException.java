package it.unisannio.authorization.exception;

import jakarta.ws.rs.WebApplicationException;

public class InvalidUserException extends WebApplicationException {
    public InvalidUserException(String message) {
        super(message);
    }
}