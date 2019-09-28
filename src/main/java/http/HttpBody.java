package http;

import static http.HttpString.CONTENT_TYPE_KEY;
import static http.HttpString.QUERY_STRING_CONTENT_TYPE;

public class HttpBody {
    private final String body;

    public HttpBody(String body) {
        this.body = body;
    }

    public boolean addQueryString(HttpHeader httpHeader, QueryString queryString) {
        if (!QUERY_STRING_CONTENT_TYPE.equals(httpHeader.getValue(CONTENT_TYPE_KEY))) {
            return false;
        }

        queryString.add(body);
        return true;
    }

    @Override
    public String toString() {
        return body;
    }
}
