package webserver.exception;

public class NotExistsMethod extends IllegalArgumentException {

    public NotExistsMethod(String s) {
        super(s);
    }
}
