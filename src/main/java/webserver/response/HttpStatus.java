package webserver.response;

public enum HttpStatus {
    Ok(200), Found(302);

    private int number;

    HttpStatus(final int number) {
        this.number = number;
    }
}
