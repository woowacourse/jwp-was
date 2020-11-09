package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;

import utils.StringUtils;

public class Request {
    private final String request;
    private final Method method;

    public Request(InputStream inputStream) throws IOException {
        if (Objects.isNull(inputStream)) {
            this.request = "";
            this.method = Method.GET;
            return;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        this.request = parse(bufferedReader);
        this.method = Method.valueOf(StringUtils.getMethod(request));
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
        return StringUtils.getFilename(request);
    }

    public Map<String, String> getQueryParameters() {
        return StringUtils.getQueryParameters(request);
    }

    @Override
    public String toString() {
        return request;
    }
}
