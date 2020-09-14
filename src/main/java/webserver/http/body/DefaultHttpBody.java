package webserver.http.body;

import webserver.http.QueryString;

public class DefaultHttpBody implements HttpBody {
    private final QueryString queryString;

    public DefaultHttpBody(String body) {
        this.queryString = QueryString.from(body);
    }

    @Override
    public String getValue(String key) {
        return this.queryString.getParameterValue(key);
    }

    @Override
    public boolean isNotEmpty() {
        return this.queryString.isNotEmpty();
    }
}
