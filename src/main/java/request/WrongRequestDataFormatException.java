package request;

public class WrongRequestDataFormatException extends RuntimeException {

    WrongRequestDataFormatException(String message) {
        super(message);
    }
}
