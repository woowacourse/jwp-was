package webserver.response;

import webserver.HttpField;

public enum ResponseHeader implements HttpField {
    LOCATION("Location"),
    SET_COOKIE("Set-Cookie");

    private final String header;

    ResponseHeader(String header) {
        this.header = header;
    }

    @Override
    public String get() {
        return header;
    }
}
