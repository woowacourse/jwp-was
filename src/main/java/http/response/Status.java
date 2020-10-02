package http.response;

public enum Status {
    OK(200, "OK"),
    FOUND(302, "Found");

    private final int code;
    private final String reasonPhrase;

    Status(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public String toString() {
        return code + " " + reasonPhrase;
    }
}
