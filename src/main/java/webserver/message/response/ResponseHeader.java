package webserver.message.response;

import webserver.message.HttpCookie;

import java.util.Map;
import java.util.stream.Collectors;

public class ResponseHeader {
    private static final String NEW_LINE = "\r\n";
    private static final String SET_COOKIE = "Set-Cookie: ";

    private final Map<String, String> responseFields;
    private final Map<String, HttpCookie> cookies;

    ResponseHeader(final Map<String, String> responseFields, final Map<String, HttpCookie> cookies) {
        this.responseFields = responseFields;
        this.cookies = cookies;
    }

    void addResponseField(final String name, String value) {
        this.responseFields.put(name, value);
    }

    void addCookie(final HttpCookie cookie) {
        this.cookies.put(cookie.getName(), cookie);
    }

    public byte[] toBytes(final int contentLength) {
        this.responseFields.put("Content-Length", Integer.toString(contentLength));

        String fields = this.responseFields.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(NEW_LINE));
        String cookies = this.cookies.values().stream()
                .map(HttpCookie::toString)
                .collect(Collectors.joining(NEW_LINE + SET_COOKIE, SET_COOKIE, ""));

        return (this.cookies.size() > 0) ? (fields + NEW_LINE + cookies).getBytes() : fields.getBytes();
    }
}
