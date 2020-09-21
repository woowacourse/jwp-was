package webserver.http.response;

public enum HttpStatus {
    OK(200), FOUND(302), BAD_REQUEST(400);

    private final int statusCode;

    HttpStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
