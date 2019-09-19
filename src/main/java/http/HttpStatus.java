package http;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found");

    private int code;
    private String phrase;

    HttpStatus(int code, String phrase) {
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
