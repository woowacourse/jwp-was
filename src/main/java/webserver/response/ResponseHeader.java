package webserver.response;

import webserver.HttpField;

public enum ResponseHeader implements HttpField {
    LOCATION("Location");

    private final String header;

    ResponseHeader(String header) {
        this.header = header;
    }

    @Override
    public String get() {
        return header;
    }
}
