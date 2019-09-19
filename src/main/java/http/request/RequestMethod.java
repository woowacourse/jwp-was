package http.request;

public enum RequestMethod {
    GET(false), DELETE(false), POST(true), PUT(true);

    private final boolean hasBody;

    private RequestMethod(boolean hasBody) {
        this.hasBody = hasBody;
    }

    public boolean hasBody() {
        return hasBody;
    }

}
