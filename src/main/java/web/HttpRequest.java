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
    static final String NEW_LINE = "\n";
    static final String EMPTY = "";
    private static final String BLANK = " ";
    private static final int PATH_INDEX = 1;
    private static final int FIRST_LINE = 0;

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
        return request.get(FIRST_LINE).split(BLANK)[PATH_INDEX];
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
