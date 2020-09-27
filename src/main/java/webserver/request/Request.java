package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import webserver.AcceptType;
import webserver.Body;
import webserver.Headers;
import webserver.HttpMethod;

public class Request {

    private RequestType requestType;
    private String version;
    private final Headers headers;
    private final Body body;
    private final AcceptType acceptType;

    public Request(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        List<String> lines = new ArrayList<>();

        while (!"".equals(line = bufferedReader.readLine()) && line != null) {
            lines.add(line);
        }
        parseRequestFirstLine(lines.remove(0));

        headers = new Headers(lines);
        body = new Body(bufferedReader, headers.getHeader("Content-Length"));
        acceptType = AcceptType.of(requestType.getPath());
    }

    private void parseRequestFirstLine(String requestFirstLine) {
        String[] splitRequestFirstLine = requestFirstLine.split(" ");

        this.requestType = RequestType.of(HttpMethod.of(splitRequestFirstLine[0]),
            splitRequestFirstLine[1].split("\\?")[0]);
        this.version = splitRequestFirstLine[2];
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public HttpMethod getMethod() {
        return requestType.getMethod();
    }

    public String getPath() {
        return requestType.getPath();
    }

    public String getVersion() {
        return version;
    }

    public AcceptType getAcceptType() {
        return acceptType;
    }

    public <T> T getBody(Class<T> type) {
        return body.bodyMapper(type);
    }

    public Body getBody() {
        return body;
    }

    public String getHeader(String name) {
        return headers.getHeader(name);
    }
}
