package webserver.http.body;

import webserver.http.QueryString;

import java.util.Objects;

public class DefaultHttpBody implements HttpBody {
    private final String body;
    private final QueryString queryString;

    public DefaultHttpBody(String body) {
        Objects.requireNonNull(body);

        this.body = body;
        this.queryString = QueryString.from(body);
    }

    @Override
    public String getValue(String key) {
        return this.queryString.getParameterValue(key);
    }

    @Override
    public String toHttpMessage() {
        return body;
    }
}
