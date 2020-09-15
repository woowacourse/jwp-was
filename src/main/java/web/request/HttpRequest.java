package web.request;

import exception.InvalidHttpRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequest {
    private final String method;
    private final RequestPath requestPath;
    private final String version;
    private final Map<String, String> requestHeader;
    private final RequestBody requestBody;

    public HttpRequest(BufferedReader request) {
        try {
            String[] requestHeaderFirstLines = request.readLine().split(" ");
            method = requestHeaderFirstLines[0].trim();
            requestPath = new RequestPath(requestHeaderFirstLines[1].trim());
            version = requestHeaderFirstLines[2].trim();

            requestHeader = mappingHeaders(request);
            requestBody = mappingBodies(request);
        } catch (IndexOutOfBoundsException | NullPointerException | IOException e) {
            throw new InvalidHttpRequestException();
        }
    }

    private Map<String, String> mappingHeaders(BufferedReader request) throws IOException {
        Map<String, String> headers = new HashMap<>();

        String line = request.readLine();
        while (!line.isEmpty()) {
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
        String line = request.readLine();
        if(Objects.isNull(line) || line.isEmpty()) {
            return new RequestBody();
        }
        return new RequestBody(line);
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
