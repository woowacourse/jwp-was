package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    public static final String NEW_LINE = System.lineSeparator();
    static final String EMPTY = "";
    private static final String BLANK = " ";
    private static final int PATH_INDEX = 1;
    private static final int REQUEST_LINE_INDEX = 0;

    private final List<String> request;

    HttpRequest(List<String> request) {
        this.request = request;
        logger.debug(toString());
    }

    public static HttpRequest from(BufferedReader br) throws IOException {
        List<String> request = new ArrayList<>();
        String line = br.readLine();
        do {
            request.add(line);
            line = br.readLine();
        } while (line != null && !EMPTY.equals(line));

        return new HttpRequest(request);
    }

    public String getPath() {
        String[] requestLines = getRequestLine().split(BLANK);
        return requestLines[PATH_INDEX];
    }

    private String getRequestLine() {
        return request.get(REQUEST_LINE_INDEX);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        HttpRequest that = (HttpRequest)o;
        return Objects.equals(request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "header=" + request +
                '}';
    }
}
