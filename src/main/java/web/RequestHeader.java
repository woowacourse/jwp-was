package web;

import static utils.HttpRequestParser.*;
import static web.HeaderProperty.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class RequestHeader {
    private static final String BLANK = " ";
    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;

    private final Map<String, String> header;
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

    public boolean isPost() {
        return Method.POST.equals(method);
    }

    public int getContentLength() {
        return Integer.parseInt(header.get(CONTENT_LENGTH.getName()));
    }

    public RequestUri getRequestUri() {
        return requestUri;
    }

    private String[] getRequestLine() {
        return header.get(REQUEST_LINE.getName()).split(BLANK);
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
