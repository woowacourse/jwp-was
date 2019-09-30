package http.request;

import java.util.Map;
import java.util.Objects;

public class HttpRequestHeader {
    private static final String CONTENT_LENGTH = "Content-Length";
    private Map<String, String> fields;
    private HttpCookieStore httpCookieStore;

    public HttpRequestHeader(Map<String, String> fields, HttpCookieStore httpCookieStore) {
        this.fields = fields;
        this.httpCookieStore = httpCookieStore;
    }

    public int getContentLength() {
        int length = 0;

        if (fields.containsKey(CONTENT_LENGTH)) {
            length = Integer.parseInt(fields.get(CONTENT_LENGTH));
        }

        return length;
    }

    public boolean hasSession() {
        return httpCookieStore.containsValue("SessionId");
    }

    public String getSessionId() {
        return httpCookieStore.getCookieValue("SessionId");
    }

    public String getCookieValue(String key) {
        return httpCookieStore.getCookieValue(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestHeader that = (HttpRequestHeader) o;
        return fields.equals(that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fields);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        fields.forEach((key, value) -> stringBuilder.append(key).append(": ").append(value).append("\r\n"));
        stringBuilder.append(httpCookieStore.toString());
        stringBuilder.append("\r\n");

        return stringBuilder.toString();
    }
}
