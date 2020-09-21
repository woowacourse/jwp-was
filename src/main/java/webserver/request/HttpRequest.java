package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import utils.StringUtils;

public class HttpRequest {

    private static final String HTTP_HEADER_DELIMITER = ": ";

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
        return requestHeader.getHeader(headerName);
    }

    public String getParameter(String paramName) {
        return requestLine.getParameter(paramName);
    }

    public String getBodyParameter(String paramName) {
        return requestBody.getParameter(paramName);
    }
}
