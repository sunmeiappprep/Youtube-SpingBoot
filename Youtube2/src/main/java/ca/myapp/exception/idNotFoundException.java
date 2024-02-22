package ca.myapp.exception;

public class idNotFoundException extends RuntimeException {
    public idNotFoundException(String message) {
        super(message);
    }
}