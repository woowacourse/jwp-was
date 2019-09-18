package http;

public enum HttpStatus {
    OK(200);

    private int statusCode;

    HttpStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}