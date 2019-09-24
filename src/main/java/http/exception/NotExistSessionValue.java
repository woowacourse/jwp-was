package http.exception;

public class NotExistSessionValue extends RuntimeException{
    public NotExistSessionValue(String message) {
        super(message);
    }
}
