package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import utils.RequestUtils;
import webserver.request.body.QueryBody;
import webserver.request.body.RequestBody;

public class Request {
    public static final String EMPTY = "";

    private final String data;
    private final RequestHeader header;
    private final RequestBody body;
    private final QueryParams query;
    private final String path;
    private final Method method;

    public Request(InputStream inputStream) throws IOException {
        if (Objects.isNull(inputStream)) {
            this.data = EMPTY;
            this.header = RequestHeader.empty();
            this.body = QueryBody.empty();
            this.query = QueryParams.empty();
            this.path = EMPTY;
            this.method = Method.GET;
            return;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        this.data = parse(bufferedReader);
        this.header = RequestHeader.of(data);
        this.body = QueryBody.of(bufferedReader, header.getContentLength());
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

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public RequestBody getBody() {
        return body;
    }

    public QueryParams getQuery() {
        return query;
    }

    @Override
    public String toString() {
        if (body.isEmpty()) {
            return data;
        }
        return data + System.lineSeparator() + body;
    }
}
