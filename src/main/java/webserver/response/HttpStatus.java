package webserver.response;

public enum HttpStatus {
    OK(200), FOUND(302), NOT_FOUND(404);

    private int number;

    HttpStatus(final int number) {
        this.number = number;
    }
}
