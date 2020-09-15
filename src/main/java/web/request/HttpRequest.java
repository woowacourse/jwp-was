package web.request;

import exception.InvalidHttpRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String method;
    private final RequestPath requestPath;
    private final String version;
    private final Map<String, String> headers;

    public HttpRequest(BufferedReader request) {
        try {
            String[] requestHeaderFirstLines = request.readLine().split(" ");
            method = requestHeaderFirstLines[0].trim();
            requestPath = new RequestPath(requestHeaderFirstLines[1].trim());
            version = requestHeaderFirstLines[2].trim();

            headers = new HashMap<>();
            mappingParameters(request);
        } catch (IndexOutOfBoundsException | NullPointerException | IOException e) {
            throw new InvalidHttpRequestException();
        }
    }

    private void mappingParameters(BufferedReader request) throws IOException {
        String line = request.readLine();
        while (!line.isEmpty()) {
            System.out.println(line);
            String[] splitLine = line.split(":");
            String key = splitLine[0].trim();
            String value = splitLine[1].trim();

            headers.put(key, value);
            line = request.readLine();
        }
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

    public String getContentType() {
        String acceptInfo = headers.get("Accept");

        return acceptInfo.split(",")[0];
    }
}
