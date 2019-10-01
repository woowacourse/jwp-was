package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestClientTest {
    public static final String CRLF = "\r\n";
    public static final String HTTP_1_1 = "HTTP/1.1";
    public static final String CONTENT_TYPE_NAME = "Content-Type";
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    public static final String CONTENT_LENGTH_NAME = "Content-Length";
    public static final String HEADER_DELIMITER = ": ";
    public static final String COOKIE_DELIMITER = "; ";
    public static final String COOKIE_VALUE_DELIMITER = "=";

    private String method;
    private String path;
    private Map<String, String> header;
    private Map<String, String> cookie;
    private byte[] body;

    private RequestClientTest(String method, String path, byte[] body) {
        this.method = method;
        this.path = path;
        this.header = new HashMap<String, String>() {
            {
                put("Host", "Host: localhost:8080");
                put("Connection", "keep-alive");
                put("Accept",  "*/*");
            }
        };
        this.cookie = new HashMap<>();
        this.body = body;
    }

    public static RequestClientTest get(String path) {
        return new RequestClientTest("GET", path, new byte[0]);
    }

    public static RequestClientTest post(String path) {
        return new RequestClientTest("POST", path, new byte[0]);
    }

    public RequestClientTest setHeader(Map<String, String> header) {
        this.header.putAll(header);

        return this;
    }

    public RequestClientTest setCookie(Map<String, String> cookie) {
        this.cookie.putAll(cookie);

        return this;
    }

    public RequestClientTest setFormBody(String body) {
        return setBody(body, CONTENT_TYPE_FORM);
    }

    public RequestClientTest setBody(String body, String contentType) {
        this.body = body.getBytes();
        header.put(CONTENT_TYPE_NAME, contentType);
        header.put(CONTENT_LENGTH_NAME, String.valueOf(this.body.length));

        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %s %s", method, path, HTTP_1_1)).append(CRLF);
        sb.append(toStringHeader());
        sb.append(toStringCookie());
        sb.append(CRLF);
        sb.append(new String(body));

        return sb.toString();
    }

    public String toStringCookie() {
        StringBuilder sb = new StringBuilder();
        if (cookie.size() > 0) {
            sb.append("Cookie: ");
            sb.append(cookie.entrySet().stream()
                    .map(entry -> entry.getKey() + COOKIE_VALUE_DELIMITER + entry.getValue())
                    .collect(Collectors.joining(COOKIE_DELIMITER))
            );
            sb.append(CRLF);
        }

        return sb.toString();
    }

    public String toStringHeader() {
        StringBuilder sb = new StringBuilder();
        for (String key : header.keySet()) {
            sb.append(key).append(HEADER_DELIMITER).append(header.get(key)).append(CRLF);
        }

        return sb.toString();
    }
}
