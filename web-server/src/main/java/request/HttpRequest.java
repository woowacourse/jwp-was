package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import utils.IOUtils;

/** request 형태!
 *
 * GET / HTTP/1.1                이부분을 request line 이라 함
 * Host: localhost:8080
 * Connection: keep-alive
 * Cache-Control: max-age=0
 * Upgrade-Insecure-Requests: 1
 * ...
 */
public class HttpRequest {

    private static final String LINE_SEPARATOR = "\\n";

    private RequestLine requestLine;
    private Headers headers;
    private MessageBody messageBody;

    public HttpRequest(String requestHeader, String requestBody) {
        List<String> header = new ArrayList<>(Arrays.asList(requestHeader.split(LINE_SEPARATOR)));

        requestLine = new RequestLine(header.get(0));
        header.remove(0);

        headers = new Headers(collectHeaderLinesFrom(header));
        messageBody = new MessageBody(requestBody);
    }

    public static HttpRequest readHttpRequest(InputStream httpRequestFormatInput)
            throws IOException {
        BufferedReader br = new BufferedReader(
            new InputStreamReader(httpRequestFormatInput, StandardCharsets.UTF_8));

        String requestHeader = IOUtils.readDataBeforeEmptyLine(br);
        String requestBody = "";

        if (HttpRequest.isExistRequestHeader(requestHeader, Headers.CONTENT_LENGTH)) {
            int contentLength = Integer.parseInt(HttpRequest.findHeaderValue(
                requestHeader, Headers.CONTENT_LENGTH));
            requestBody = IOUtils.readData(br, contentLength);
        }
        return new HttpRequest(requestHeader, requestBody);
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
        return new HttpRequest(request, MessageBody.EMPTY_BODY)
            .headers
            .isExist(headerName);
    }

    public static String findHeaderValue(String request, String headerName) {
        return new HttpRequest(request, MessageBody.EMPTY_BODY)
            .getHeader(headerName);
    }

    public String getValueFromFormData(String headerName) {
        return messageBody.findDataFromFormFormatBody(headerName);
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

    public String getUriPath() {
        return requestLine.getUriPath();
    }

    public boolean isHeaderExist(String headerName) {
        return headers.isExist(headerName);
    }

    public String getHeader(String headerName) {
        return headers.getValue(headerName);
    }

    @Override
    public String toString() {
        return "** HttpRequest **\n"
            + requestLine.toString()
            + "\n"
            + headers.toString()
            + "\n"
            + messageBody.toString()
            + "\n";
    }
}
