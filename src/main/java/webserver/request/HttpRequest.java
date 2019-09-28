package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private final RequestStatusLine requestStatusLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    private HttpRequest(RequestStatusLine requestStatusLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestStatusLine = requestStatusLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest of(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        RequestStatusLine requestStatusLine = RequestStatusLine.of(br);
        RequestHeader requestHeader = RequestHeader.of(br);
        RequestBody requestBody = RequestBody.of(br, requestHeader.getContentLength());
        return new HttpRequest(requestStatusLine, requestHeader, requestBody);
    }

    public boolean isGet() {
        return requestStatusLine.isGet();
    }

    public boolean isPost() {
        return requestStatusLine.isPost();
    }

    public boolean hasParameters() {
        return requestStatusLine.hasParameters();
    }

    public String getParameter(String attributeName) {
        if (hasParameters()) {
            return requestStatusLine.getParameterValue(attributeName);
        }
        return requestBody.getParameterValue(attributeName);
    }

    public String getHttpVersion() {
        return requestStatusLine.getHttpVersion();
    }

    public String getSource() {
        return requestStatusLine.getPath();
    }

    public boolean containHeaderField(String headerField, String value) {
        return requestHeader.contains(headerField, value);
    }

    public String getHeaderFieldValue(String headerField) {
        return requestHeader.getHeaderFieldValue(headerField);
    }

    public RequestStatusLine getRequestStatusLine() {
        return requestStatusLine;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
