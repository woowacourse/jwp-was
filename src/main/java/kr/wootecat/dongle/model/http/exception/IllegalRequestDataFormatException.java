package kr.wootecat.dongle.model.http.exception;

public class IllegalRequestDataFormatException extends RuntimeException {

    public IllegalRequestDataFormatException(String message) {
        super(message);
    }
}
