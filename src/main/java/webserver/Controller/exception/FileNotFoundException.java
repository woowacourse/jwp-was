package webserver.Controller.exception;

public class FileNotFoundException extends NotFoundException {
    public FileNotFoundException(String message) {
        super(message);
    }
}
