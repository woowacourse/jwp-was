package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHeader {
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);
    public static final String NEW_LINE = System.lineSeparator();
    static final String EMPTY = "";
    private static final String BLANK = " ";
    private static final int PATH_INDEX = 1;
    private static final int REQUEST_LINE_INDEX = 0;

    private RequestUri requestUri;
    private final List<String> request;

    RequestHeader(List<String> request) {
        this.request = request;
        this.requestUri = new RequestUri(getRequestLine().split(BLANK)[PATH_INDEX]);
        logger.debug(toString());
    }

    public RequestHeader(BufferedReader br) throws IOException {
        List<String> request = new ArrayList<>();
        String line = br.readLine();
        do {
            request.add(line);
            line = br.readLine();
        } while (line != null && !EMPTY.equals(line));

        this.request = request;
        this.requestUri = new RequestUri(getRequestLine().split(BLANK)[PATH_INDEX]);
        logger.debug(toString());
    }

    public RequestUri getRequestUri() {
        return requestUri;
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
        RequestHeader that = (RequestHeader)o;
        return Objects.equals(request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request);
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "request=" + request +
                '}';
    }
}
