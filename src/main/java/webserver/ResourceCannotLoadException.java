package webserver;

public class ResourceCannotLoadException extends ServerException {
    public ResourceCannotLoadException(String message) {
        super(message);
    }
}
