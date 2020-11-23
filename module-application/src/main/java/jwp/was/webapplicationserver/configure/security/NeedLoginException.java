package jwp.was.webapplicationserver.configure.security;

public class NeedLoginException extends RuntimeException {

    public NeedLoginException(String message) {
        super(message);
    }
}
