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

    private final String data;
    private final Map<String, String> body;
    private final QueryParams query;
    private final String path;
    private final Method method;
    private final RequestHeader header;

    public Request(InputStream inputStream) throws IOException {
        if (Objects.isNull(inputStream)) {
            this.data = EMPTY;
            this.header = RequestHeader.empty();
            this.body = Collections.emptyMap();
            this.query = QueryParams.empty();
            this.path = EMPTY;
            this.method = Method.GET;
            return;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        this.data = parse(bufferedReader);
        this.header = RequestHeader.of(data);
        this.body = parseBody(bufferedReader);
        this.query = QueryParams.of(data);
        this.path = RequestUtils.getFilename(data);
        this.method = RequestUtils.getMethod(data);
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
        int contentLength = header.getContentLength();
        if (contentLength == 0) {
            return Collections.emptyMap();
        }
        return RequestUtils.getBody(IOUtils.readData(bufferedReader, contentLength));
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public QueryParams getQuery() {
        return query;
    }

    @Override
    public String toString() {
        if (body.keySet().size() == 0) {
            return data;
        }
        return data + System.lineSeparator() + body;
    }
}
