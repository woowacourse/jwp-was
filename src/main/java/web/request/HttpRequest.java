package web.request;

import exception.InvalidHttpRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public HttpRequest(BufferedReader request) {
        try {
            requestLine = new RequestLine(request.readLine());
            requestHeader = new RequestHeader(request);
            requestBody = mappingBodies(request);
        } catch (IndexOutOfBoundsException | NullPointerException | IOException e) {
            throw new InvalidHttpRequestException();
        }
    }

    private RequestBody mappingBodies(BufferedReader request) throws IOException {
        MethodType method = requestLine.getMethod();
        if (!method.isPost()) {
            return new RequestBody();
        }
        int contentLength = requestHeader.getContentLength();
        String requestBodyData = IOUtils.readData(request, contentLength);
        if (requestBodyData.isEmpty()) {
            return new RequestBody();
        }
        return new RequestBody(requestBodyData);
    }

    public MethodType getMethod() {
        return requestLine.getMethod();
    }

    public RequestPath getRequestPath() {
        return requestLine.getRequestPath();
    }

    public String getVersion() {
        return requestLine.getVersion();
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public String getContentType() {
        return requestHeader.getContentType();
    }
}
