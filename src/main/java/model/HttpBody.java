package model;

import static jdk.internal.joptsimple.internal.Strings.*;

public class HttpBody {
    public static final HttpBody EMPTY_BODY = new HttpBody(EMPTY);

    private final String body;

    public HttpBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
