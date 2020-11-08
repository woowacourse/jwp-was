package http.header;

import exception.InvalidContentLengthException;
import exception.InvalidHttpMessageException;
import http.body.HttpBody;
import utils.IOUtils;
import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class HttpHeaders {
    public static final String HEADER_VALUE_DELIMITER = ";";

    private final List<HttpHeader> httpHeaders;

    public HttpHeaders(List<HttpHeader> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public static HttpHeaders from(BufferedReader br) throws IOException {
        List<HttpHeader> httpHeaders = new ArrayList<>();

        while (true) {
            String header = br.readLine();

            if (Objects.isNull(header)) {
                throw new InvalidHttpMessageException("null");
            }

            if (header.isEmpty()) {
                break;
            }

            httpHeaders.add(HttpHeader.from(header));
        }

        return new HttpHeaders(httpHeaders);
    }

    public static HttpHeaders empty() {
        return new HttpHeaders(new ArrayList<>());
    }

    public void addHeader(HttpHeader httpHeader) {
        this.httpHeaders.add(httpHeader);
    }

    public HttpBody createHttpBody(BufferedReader br) throws IOException {
        int contentLength = getContentLength();
        String body = contentLength == 0 ? "" : IOUtils.readData(br, contentLength);

        return getContentType().createHttpBody(body);
    }

    private int getContentLength() {
        Optional<String> contentLengthValue = getHeaderValue(HttpHeaderName.CONTENT_LENGTH.getName());
        String contentLength = contentLengthValue.orElse(null);

        if (Objects.isNull(contentLength)) {
            return 0;
        }

        if (StringUtils.isNotNumber(contentLength)) {
            throw new InvalidContentLengthException(contentLength);
        }

        return Integer.parseInt(contentLength);
    }

    private HttpContentType getContentType() {
        Optional<String> contentType = getHeaderValue(HttpHeaderName.CONTENT_TYPE.getName());

        return contentType.map(HttpContentType::from)
                .orElse(HttpContentType.APPLICATION_X_WWW_FORM_URLENCODED);
    }

    public Optional<String> getHeaderValue(String headerName) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidHttpMessageException(headerName), headerName);

        return this.httpHeaders.stream()
                .filter(httpHeader -> httpHeader.hasSameName(headerName))
                .map(HttpHeader::getValue)
                .findFirst();
    }

    public String toHttpMessage() {
        return this.httpHeaders.stream()
                .map(HttpHeader::toHttpMessage)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
