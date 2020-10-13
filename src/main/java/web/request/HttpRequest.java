package web.request;

import exception.InvalidHttpRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import web.HttpHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestLine requestLine;
    private final HttpHeader httpHeader;
    private final RequestBody requestBody;

    public HttpRequest(InputStream inputStream) {
        try {
            BufferedReader request = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            requestLine = new RequestLine(request.readLine());
            httpHeader = new HttpHeader(mappingHeaders(request));
            requestBody = mappingBodies(request);
        } catch (IndexOutOfBoundsException | NullPointerException | IOException e) {
            throw new InvalidHttpRequestException();
        }
    }

    private Map<String, String> mappingHeaders(BufferedReader request) throws IOException {
        Map<String, String> headers = new HashMap<>();

        String line = request.readLine();
        while (!Objects.isNull(line) && !line.isEmpty()) {
            logger.debug(line);
            String[] splitLine = line.split(":");
            String key = splitLine[0].trim();
            String value = splitLine[1].trim();

            headers.put(key, value);
            line = request.readLine();
        }
        return headers;
    }

    private RequestBody mappingBodies(BufferedReader request) throws IOException {
        MethodType method = requestLine.getMethod();
        if (!method.isPost()) {
            return new RequestBody();
        }
        int contentLength = httpHeader.getContentLength();
        String requestBodyData = IOUtils.readData(request, contentLength);
        if (requestBodyData.isEmpty()) {
            return new RequestBody();
        }
        return new RequestBody(requestBodyData);
    }

    public MethodType getMethod() {
        return requestLine.getMethod();
    }

    public String getVersion() {
        return requestLine.getVersion();
    }

    public String getAcceptType() {
        return httpHeader.getAcceptType();
    }

    public String getTarget() {
        RequestPath requestPath = requestLine.getRequestPath();
        return requestPath.getTarget();
    }

    public String getRequestParamsByKey(String key) {
        RequestPath requestPath = requestLine.getRequestPath();
        return requestPath.getParameterByKey(key);
    }

    public String getRequestHeaderByKey(String key) {
        return httpHeader.getHeaderByKey(key);
    }

    public String getRequestBodyByKey(String key) {
        return requestBody.getParameterByKey(key);
    }
}
