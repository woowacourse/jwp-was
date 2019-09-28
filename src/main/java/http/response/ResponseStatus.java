package http.response;

public enum ResponseStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed");

    private static final String BLANK = " ";
    private int code;
    private String phrase;

    ResponseStatus(int code, String phrase) {
        this.code = code;
        this.phrase = phrase;
    }

    public static ResponseStatus byCode(int code) {
        for (ResponseStatus responseStatus : ResponseStatus.values()) {
            if (code == responseStatus.code) {
                return responseStatus;
            }
        }
        throw new IllegalArgumentException("illegal error code");
    }

    public int getCode() {
        return code;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getCodeAndPhrase() {
        return code + BLANK + phrase;
    }
}
