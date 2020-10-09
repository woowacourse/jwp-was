package webserver.http.header;

import exception.InvalidContentLengthException;
import exception.InvalidHttpMessageException;
import utils.IOUtils;
import utils.StringUtils;
import webserver.http.body.HttpBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpHeader {
    private static final String HEADER_LINE_DELIMITER = ":";
    public static final String HEADER_VALUE_DELIMITER = ";";

    private final Map<String, String> headers;

    private HttpHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeader from(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();

        while (true) {
            String headerLine = br.readLine();

            if (Objects.isNull(headerLine)) {
                throw new InvalidHttpMessageException("null");
            }

            if (headerLine.isEmpty()) {
                break;
            }

            String[] headerNameAndValue = parseHeaderNameAndValue(headerLine);
            headers.put(headerNameAndValue[0], headerNameAndValue[1]);
        }

        return new HttpHeader(headers);
    }

    public static HttpHeader from(Map<String, String> headers) {
        if (Objects.isNull(headers) || headers.isEmpty()) {
            throw new InvalidHttpMessageException(headers);
        }

        headers.forEach(HttpHeader::validateHeader);

        return new HttpHeader(headers);
    }

    private static String[] parseHeaderNameAndValue(String headerLine) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidHttpMessageException(headerLine), headerLine);

        int headerNameIndex = headerLine.indexOf(HEADER_LINE_DELIMITER);
        if (headerNameIndex == -1) {
            throw new InvalidHttpMessageException(headerLine);
        }

        String headerName = headerLine.substring(0, headerNameIndex).trim();
        String headerValue = headerLine.substring(headerNameIndex + 1).trim();

        if (headerName.isEmpty() || headerValue.isEmpty()) {
            throw new InvalidHttpMessageException(headerLine);
        }

        return new String[]{headerName, headerValue};
    }

    private static void validateHeader(String key, String value) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidHttpMessageException(key, value), key, value);

        String headerName = key.trim();
        String headerValue = value.trim();

        if (headerName.isEmpty() || headerValue.isEmpty()) {
            throw new InvalidHttpMessageException(headerName, headerValue);
        }
    }

    public HttpBody createHttpBody(BufferedReader br) throws IOException {
        int contentLength = getContentLength();
        String body = contentLength == -1 ? "" : IOUtils.readData(br, contentLength);

        return getContentType().createHttpBody(body);
    }

    private int getContentLength() {
        String contentLength = this.headers.get(HttpHeaderField.CONTENT_LENGTH.getName());
        if (Objects.isNull(contentLength)) {
            return -1;
        }

        if (StringUtils.isNotNumber(contentLength)) {
            throw new InvalidContentLengthException(contentLength);
        }

        return Integer.parseInt(contentLength);
    }

    private HttpContentType getContentType() {
        String contentType = this.headers.get(HttpHeaderField.CONTENT_TYPE.getName());
        return HttpContentType.from(contentType);
    }

    public String getHeaderValue(String headerField) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidHttpMessageException(headerField), headerField);

        return this.headers.get(headerField);
    }

    public String toHttpMessage() {
        return this.headers.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
