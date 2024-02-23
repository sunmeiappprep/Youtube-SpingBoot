package ca.myapp.exception;

public class ErrorToFrontEnd extends RuntimeException {
    public ErrorToFrontEnd(String message) {
        super(message);
    }
}