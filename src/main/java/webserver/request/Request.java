package webserver.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** request 형태!
 *
 * GET / HTTP/1.1                이부분을 request line 이라 함
 * Host: localhost:8080
 * Connection: keep-alive
 * Cache-Control: max-age=0
 * Upgrade-Insecure-Requests: 1
 * ...
 */
public class Request {

    private static final String LINE_SEPARATOR = "\\n";

    private RequestLine requestLine;
    private Headers headers;
    private MessageBody messageBody;

    public Request(String requestHeader, String requestBody) {
        List<String> header = new ArrayList<>(Arrays.asList(requestHeader.split(LINE_SEPARATOR)));

        requestLine = new RequestLine(header.get(0));
        header.remove(0);

        headers = new Headers(collectHeaderLinesFrom(header));
        messageBody = new MessageBody(requestBody);
    }

    private List<String> collectHeaderLinesFrom(List<String> lines) {
        List<String> headers = new ArrayList<>();

        while (!lines.isEmpty() && !lines.get(0).isEmpty()) {
            headers.add(lines.get(0));
            lines.remove(0);
        }
        return Collections.unmodifiableList(headers);
    }

    public static boolean isExistRequestHeader(String request, String headerName) {
        return new Request(request, "")
            .headers
            .doesHeaderExist(headerName);
    }

    public static String findHeaderValue(String request, String headerName) {
        return new Request(request, "")
            .getHeader(headerName);
    }

    public boolean isUriUsingQueryString() {
        return requestLine.isUriUsingQueryString();
    }

    public Map<String, String > getQueryDataFromUri() {
        return requestLine.getQueryDataFromUri();
    }

    public Map<String, String> getFormDataFromBody() {
        return messageBody.getFormDataFromBody();
    }

    public boolean isUriPath(String uriPath) {
        return requestLine.isUriPath(uriPath);
    }

    public boolean isMethod(Method method) {
        return requestLine.isMethod(method);
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public String getUri() {
        return requestLine.getUri();
    }

    public String getHeader(String headerName) {
        return headers.getValue(headerName);
    }
}
