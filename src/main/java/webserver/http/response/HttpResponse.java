package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponse {
    private static final String NEW_LINE = "\r\n";
    public static final String COOKIE_KEY_VALUE_DELIMITER = "=";
    public static final String COOKIE_VALUES_DELIMITER = ";";
    private final String HEADER_DELIMITER = ": ";
    private final String LINE_DELIMITER = " ";
    private Map<String, String> cookies = new HashMap<>();
    private HttpVersion httpVersion;
    private DataOutputStream dos;
    private HttpStatus httpStatus;
    private Map<String, Object> headers = new HashMap<>();

    public HttpResponse(DataOutputStream dos, HttpVersion httpVersion) {
        this.dos = dos;
        this.httpVersion = httpVersion;
    }

    public void ok() {
        httpStatus = HttpStatus.OK;
    }

    public void redirect(String location) {
        httpStatus = HttpStatus.FOUND;
        appendHeader("Location", location);
    }

    public void error(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void errorWrite() throws IOException {
        writeLine();
        writeHeader();
        end();
    }
    public void appendHeader(String key, Object value) {
        headers.put(key, value);
    }

    public void appendContentHeader(String memeName, int length) {
        appendHeader("Content-Type", memeName + "; charset=utf-8");
        appendHeader("Content-Length", length);
    }

    public void setCookie(String key, String value) {
        cookies.put(key, value);
    }

    private String parseCookies(Map<String, String> cookies) {
        return cookies.entrySet().stream()
                .map(s -> String.join(COOKIE_KEY_VALUE_DELIMITER, s.getKey(), s.getValue()) + COOKIE_VALUES_DELIMITER)
                .collect(Collectors.joining(" "));
    }

    public void writeLine() throws IOException {
        dos.writeBytes(httpVersion.name() + LINE_DELIMITER + httpStatus.getCode() + LINE_DELIMITER + httpStatus.getName() + NEW_LINE);
    }

    public void writeHeader() throws IOException {
        appendCookieHeader();
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            dos.writeBytes(entry.getKey() + HEADER_DELIMITER + entry.getValue() + NEW_LINE);
        }
    }

    private void appendCookieHeader() {
        if (!cookies.isEmpty()) {
            appendHeader("Set-Cookie", parseCookies(cookies) + " Path=/");
        }
    }

    public void writeBody(byte[] body) throws IOException {
        dos.writeBytes(NEW_LINE);
        dos.write(body, 0, body.length);
    }

    public void end() throws IOException {
        dos.flush();
    }

}


