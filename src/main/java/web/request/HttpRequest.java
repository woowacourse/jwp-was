package web.request;

import exception.InvalidHttpRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String target;
    private final String version;
    private final Map<String, String> parameters;

    public HttpRequest(BufferedReader request) {
        try {
            String[] requestHeaderFirstLines = request.readLine().split(" ");
            method = requestHeaderFirstLines[0].trim();
            target = requestHeaderFirstLines[1].trim();
            version = requestHeaderFirstLines[2].trim();

            parameters = new HashMap<>();
            mappingParameters(request);
        }catch(IndexOutOfBoundsException | NullPointerException | IOException e) {
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

            parameters.put(key, value);
            line = request.readLine();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getTarget() {
        return target;
    }

    public String getVersion() {
        return version;
    }

    public String getContentType() {
        String acceptInfo = parameters.get("Accept");

        return acceptInfo.split(",")[0];
    }
}
