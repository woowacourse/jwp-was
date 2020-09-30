package webserver.http.body;

import exception.InvalidParameterException;
import webserver.http.QueryString;

import java.util.Objects;

public class DefaultHttpBody implements HttpBody {
    private final String body;
    private final QueryString queryString;

    private DefaultHttpBody(String body, QueryString queryString) {
        this.body = body;
        this.queryString = queryString;
    }

    public static DefaultHttpBody from(String body) {
        Objects.requireNonNull(body);

        QueryString queryString;
        try {
            queryString = QueryString.from(body);
        } catch (InvalidParameterException e) {
            queryString = QueryString.empty();
        }

        return new DefaultHttpBody(body, queryString);
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
