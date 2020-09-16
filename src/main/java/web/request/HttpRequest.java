package web.request;

import exception.InvalidHttpRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final String method;
    private final RequestPath requestPath;
    private final String version;
    private final Map<String, String> requestHeader;
    private final RequestBody requestBody;

    public HttpRequest(BufferedReader request) {
        try {
            String requestHeaderFirstLine = request.readLine();
            logger.debug(requestHeaderFirstLine);
            String[] tokens = requestHeaderFirstLine.split(" ");
            method = tokens[0].trim();
            requestPath = new RequestPath(tokens[1].trim());
            version = tokens[2].trim();

            requestHeader = mappingHeaders(request);
            requestBody = mappingBodies(request);
        } catch (IndexOutOfBoundsException | NullPointerException | IOException e) {
            throw new InvalidHttpRequestException();
        }
    }

    private Map<String, String> mappingHeaders(BufferedReader request) throws IOException, NullPointerException {
        Map<String, String> headers = new HashMap<>();

        String line = request.readLine();
        while (Objects.isNull(line) || !line.isEmpty()) {
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
        if(!method.equals("POST")) {
            return new RequestBody();
        }
        int contentLength = Integer.parseInt(requestHeader.get("Content-Length"));
        String requestBodyData = IOUtils.readData(request, contentLength);
        if(Objects.isNull(requestBodyData) || requestBodyData.isEmpty()) {
            return new RequestBody();
        }
        return new RequestBody(requestBodyData);
    }

    public String getMethod() {
        return method;
    }

    public RequestPath getRequestPath() {
        return requestPath;
    }

    public String getVersion() {
        return version;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public String getContentType() {
        String acceptInfo = requestHeader.get("Accept");

        return acceptInfo.split(",")[0];
    }
}
