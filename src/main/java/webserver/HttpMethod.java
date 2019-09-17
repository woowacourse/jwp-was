package webserver;

public enum HttpMethod {
    GET(false), DELETE(false), POST(true), PUT(true);

    private final boolean hasBody;

    private HttpMethod(boolean hasBody) {
        this.hasBody = hasBody;
    }

    public boolean hasBody() {
        return hasBody;
    }

}
