package kr.wootecat.dongle.http.exception;

public class IllegalRequestDataFormatException extends RuntimeException {

    public IllegalRequestDataFormatException(String message) {
        super(message);
    }
}
