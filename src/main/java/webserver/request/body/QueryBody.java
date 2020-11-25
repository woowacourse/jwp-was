package webserver.request.body;

import utils.IOUtils;
import utils.RequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class QueryBody implements RequestBody{
    private final Map<String, String> body;

    private QueryBody(Map<String, String> body) {
        this.body = body;
    }

    public static QueryBody of(BufferedReader bufferedReader, int length) throws IOException {
        if (length == 0) {
            return empty();
        }
        return new QueryBody(RequestUtils.getQueryBody(IOUtils.readData(bufferedReader, length)));
    }

    public static QueryBody empty() {
        return new QueryBody(Collections.emptyMap());
    }

    @Override
    public String get(String key) {
        return body.get(key);
    }

    @Override
    public boolean isEmpty() {
        return body.size() == 0;
    }
}
