package http.request;

import http.request.exception.NonExistentMethodException;

public enum RequestMethod {
    GET(false), DELETE(false), POST(true), PUT(true);

    private final boolean hasBody;

    RequestMethod(boolean hasBody) {
        this.hasBody = hasBody;
    }

    public static RequestMethod of(String methodName) {
        try {
            return valueOf(methodName);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new NonExistentMethodException();
        }
    }

    public boolean hasBody() {
        return hasBody;
    }

}
