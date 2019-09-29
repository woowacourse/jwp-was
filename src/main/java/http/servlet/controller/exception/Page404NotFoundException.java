package http.servlet.controller.exception;

public class Page404NotFoundException extends RuntimeException {
    private static final String MESSAGE_OF_404_ERROR = "404 Page NOT FOUND\n";

    public Page404NotFoundException() {
        super(MESSAGE_OF_404_ERROR);
    }
}
