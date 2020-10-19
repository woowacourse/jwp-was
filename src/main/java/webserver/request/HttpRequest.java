package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import utils.StringUtils;
import webserver.Cookie;

public class HttpRequest {

    private static final String HTTP_HEADER_DELIMITER = ": ";
    public static final String HTTP_HEADER_VALUE_DELIMITER = ",";
    public static final String COOKIE_HEADER = "Cookie";

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        requestLine = new RequestLine(line);
        requestHeader = new RequestHeader(new HashMap<>());

        while (!StringUtils.isBlank(line = bufferedReader.readLine())) {
            String[] lineSegment = line.split(HTTP_HEADER_DELIMITER);
            if (isNotAccessibleLine(lineSegment)) {
                continue;
            }
            requestHeader.putHeader(lineSegment[0], lineSegment[1]);
        }
        requestBody = new RequestBody(bufferedReader, requestHeader, requestLine);
    }

    private boolean isNotAccessibleLine(String[] lineSegment) {
        return lineSegment.length != 2;
    }

    public boolean isMethodSupported() {
        return requestLine.isSupported();
    }

    public boolean isMethodNotImplemented() {
        return requestLine.isNotImplemented();
    }

    public String getBody() {
        return requestBody.getBody();
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String headerName) {
        String header = requestHeader.getHeader(headerName);
        return getFirstHeaderSegment(header);
    }

    private String getFirstHeaderSegment(String header) {
        return header.split(HTTP_HEADER_VALUE_DELIMITER)[0];
    }

    public String getParameter(String paramName) {
        return requestLine.getParameter(paramName);
    }

    public String getBodyParameter(String paramName) {
        return requestBody.getParameter(paramName);
    }

    public List<Cookie> getCookies() {
        String cookieHeader = requestHeader.getHeader(COOKIE_HEADER);

        return Cookie.parse(cookieHeader);
    }
}
