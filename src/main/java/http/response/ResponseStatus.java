package http.response;

public enum ResponseStatus {
    OK(200, "OK"),
    FOUND(302, "Found");

    private int code;
    private String phrase;

    ResponseStatus(int code, String phrase) {
        this.code = code;
        this.phrase = phrase;
    }

    public int getCode() {
        return code;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getInfo() {
        return code + " " + phrase;
    }
}
