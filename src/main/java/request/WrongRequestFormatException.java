package request;

public class WrongRequestFormatException extends RuntimeException {

    WrongRequestFormatException(String message) {
        super(message);
    }
}
