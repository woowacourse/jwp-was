package kr.wootecat.dongle.model.http.request;

import static kr.wootecat.dongle.model.http.HttpMethod.*;
import static kr.wootecat.dongle.model.http.request.ProtocolVersion.*;
import static kr.wootecat.dongle.model.http.request.Url.*;

import kr.wootecat.dongle.model.http.HttpMethod;
import kr.wootecat.dongle.model.http.exception.IllegalRequestDataFormatException;

public class HttpRequestLine {

    private static final String HTTP_SPACE_CHARACTER = " ";

    private static final int COUNT_OF_REQUEST_LINE_DATA = 3;

    private static final String ILLEGAL_REQUEST_LINE_FORMAT_EXCEPTION_MESSAGE = "올바른 Request line 형식이 아닙니다.";

    private final HttpMethod method;
    private final Url url;
    private final ProtocolVersion version;

    public HttpRequestLine(HttpMethod method, Url url, ProtocolVersion version) {
        this.method = method;
        this.url = url;
        this.version = version;
    }

    public static HttpRequestLine from(String requestLine) {
        String[] requestLines = requestLine.split(HTTP_SPACE_CHARACTER);
        if (requestLines.length != COUNT_OF_REQUEST_LINE_DATA) {
            throw new IllegalRequestDataFormatException(ILLEGAL_REQUEST_LINE_FORMAT_EXCEPTION_MESSAGE);
        }

        String methodType = requestLines[0];
        String url = requestLines[1];
        String version = requestLines[2];

        return new HttpRequestLine(HttpMethod.of(methodType), Url.from(url), ProtocolVersion.of(version));
    }

    public static HttpRequestLine internalErrorPage() {
        return new HttpRequestLine(GET, INTERNAL_ERROR_PAGE_URL, HTTP_1_1);
    }

    public boolean isFileRequest() {
        return url.isFileRequest();
    }

    public boolean isParamEmpty() {
        return url.isParamEmpty();
    }

    public String getVersion() {
        return version.getVersion();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public boolean isGetMethod() {
        return method.isGet();
    }

    public String getPath() {
        return url.getPath();
    }

    public String getParameter(String name) {
        return url.getParameter(name);
    }
}
