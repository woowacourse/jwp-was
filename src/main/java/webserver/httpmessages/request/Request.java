package webserver.httpmessages.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public Request(String httpRequest) {
        List<String> lines = new ArrayList<>(Arrays.asList(httpRequest.split(LINE_SEPARATOR)));

        requestLine = new RequestLine(lines.get(0));

        lines.remove(0);
        headers = new Headers(lines);
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
