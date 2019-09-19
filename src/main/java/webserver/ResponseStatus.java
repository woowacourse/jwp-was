package webserver;

public enum ResponseStatus {
    OK(200), FOUND(302);

    private final int statusCode;

    ResponseStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String serialize() {
        return String.format("%s %s", String.valueOf(statusCode), this.name());
    }
}
