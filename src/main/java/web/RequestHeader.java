package web;

import static utils.HttpRequestParser.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHeader {
    public static final String NEW_LINE = System.lineSeparator();
    public static final int CONTENT_LENGTH_INDEX = 3;
    public static final int VALUE_INDEX = 1;
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);
    private static final String BLANK = " ";
    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int REQUEST_LINE_INDEX = 0;
    private final List<String> header;
    private Method method;
    private RequestUri requestUri;

    public RequestHeader(BufferedReader br) throws IOException {
        this.header = parsingRequestHeader(br);
        this.method = Method.valueOf(getRequestLine()[METHOD_INDEX]);
        this.requestUri = new RequestUri(getRequestLine()[PATH_INDEX]);
    }

    public boolean isStaticFile() {
        return requestUri.isStaticFile();
    }

    public boolean isRootPath() {
        return requestUri.isRootPath();
    }

    public boolean isPost() {
        return Method.POST.equals(method);
    }

    public int getContentLength() {
        return Integer.parseInt(header.get(CONTENT_LENGTH_INDEX).split(BLANK)[VALUE_INDEX]);
    }

    public RequestUri getRequestUri() {
        return requestUri;
    }

    private String[] getRequestLine() {
        return header.get(REQUEST_LINE_INDEX).split(BLANK);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RequestHeader that = (RequestHeader)o;
        return method == that.method &&
                Objects.equals(requestUri, that.requestUri) &&
                Objects.equals(header, that.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, requestUri, header);
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "method=" + method +
                ", requestUri=" + requestUri +
                ", header=" + header +
                '}';
    }
}
