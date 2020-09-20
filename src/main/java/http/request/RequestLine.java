package http.request;

import java.io.BufferedReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import exceptions.InvalidHttpRequestException;
import http.HttpMethod;
import http.HttpVersion;
import utils.IOUtils;

public class RequestLine {

    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);
    private static final String REQUEST_LINE_DELIMITER = " ";

    private final HttpMethod method;
    private final RequestPath path;
    private final HttpVersion version;

    public RequestLine(BufferedReader bufferedReader) {
        String requestLine = IOUtils.readRequestLine(bufferedReader);
        logger.info("{}", requestLine);
        if (StringUtils.isEmpty(requestLine)) {
            logger.info("올바른 HTTP 요청이 아닙니다. RequestLine을 확인해주세요");
            throw new InvalidHttpRequestException();
        }
        String[] splitRequestLine = requestLine.split(REQUEST_LINE_DELIMITER);
        this.method = HttpMethod.of(splitRequestLine[0]);
        this.path = RequestPath.of(splitRequestLine[1]);
        this.version = HttpVersion.of(splitRequestLine[2]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path.getPath();
    }

    public String getParameters(String key) {
        return path.getParameters(key);
    }

    public String getParam(String key) {
        return path.getParameters(key);
    }

    public HttpVersion getVersion() {
        return version;
    }
}
