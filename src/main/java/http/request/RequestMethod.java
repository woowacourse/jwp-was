package http.request;

import http.request.exception.InvalidRequestMethodException;

public enum RequestMethod {
    GET(false), DELETE(false), POST(true), PUT(true);

    private final boolean hasBody;

    private RequestMethod(boolean hasBody) {
        this.hasBody = hasBody;
    }

    public static RequestMethod of(String methodName) {
        try {
            return valueOf(methodName);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidRequestMethodException();
        }
    }

    public boolean hasBody() {
        return hasBody;
    }

}
