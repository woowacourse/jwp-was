package http;

public class HttpBody {
    private static final String QUERY_STRING_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE_KEY = "Content-Type";

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
