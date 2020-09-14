package webserver.exception;

public class NotExistsUrlPath extends IllegalArgumentException {

    public NotExistsUrlPath(String message) {
        super(message);
    }
}
