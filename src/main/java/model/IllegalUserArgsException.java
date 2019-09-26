package model;

public class IllegalUserArgsException extends RuntimeException {
    public IllegalUserArgsException() {
    }

    public IllegalUserArgsException(String message) {
        super(message);
    }
}
