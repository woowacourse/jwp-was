package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import utils.IOUtils;
import utils.RequestUtils;

public class Request {
    public static final String EMPTY = "";

    private final String request;
    private final Method method;
    private final Map<String, String> header;
    private final Map<String, String> body;

    public Request(InputStream inputStream) throws IOException {
        if (Objects.isNull(inputStream)) {
            this.request = EMPTY;
            this.method = Method.GET;
            this.header = Collections.emptyMap();
            this.body = Collections.emptyMap();
            return;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        this.request = parse(bufferedReader);
        this.method = Method.valueOf(RequestUtils.getMethod(request));
        this.header = RequestUtils.getHeader(request);
        this.body = parseBody(bufferedReader);
    }

    private String parse(BufferedReader bufferedReader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (Objects.nonNull(line) && !line.isEmpty()) {
            builder.append(line).append(System.lineSeparator());
            line = bufferedReader.readLine();
        }
        return builder.toString();
    }

    private Map<String, String> parseBody(BufferedReader bufferedReader) throws IOException {
        int contentLength = Integer.parseInt(header.getOrDefault("Content-Length", "0"));
        if (contentLength == 0) {
            return Collections.emptyMap();
        }
        return RequestUtils.getBody(IOUtils.readData(bufferedReader, contentLength));
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return RequestUtils.getFilename(request);
    }

    public Map<String, String> getBody() {
        return body;
    }

    public Map<String, String> getQueryParameters() {
        return RequestUtils.getQueryParameters(request);
    }

    @Override
    public String toString() {
        if (body.keySet().size() == 0) {
            return request;
        }
        return request + System.lineSeparator() + body;
    }
}
