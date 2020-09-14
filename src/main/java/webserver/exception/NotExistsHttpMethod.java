package webserver.exception;

public class NotExistsHttpMethod extends IllegalArgumentException {

    public NotExistsHttpMethod(String s) {
        super(s);
    }
}
