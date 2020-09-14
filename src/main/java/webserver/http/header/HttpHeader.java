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

public class HttpHeader {
    private final Map<String, String> headers;

    public HttpHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public HttpBody createHttpBody(BufferedReader br) throws IOException {
        int contentLength = getContentLength();
        String body = contentLength == -1 ? "" : IOUtils.readData(br, contentLength);

        return getContentType().createHttpBody(body);
    }

    private int getContentLength() {
        String contentLength = this.headers.get("Content-Length");
        if (Objects.isNull(contentLength)) {
            return -1;
        }

        if (StringUtils.isNotNumber(contentLength)) {
            throw new InvalidContentLengthException(contentLength);
        }

        return Integer.parseInt(contentLength);
    }

    private HttpContentType getContentType() {
        String contentType = this.headers.get("Content-Type");
        return HttpContentType.from(contentType);
    }

    public static class Builder {
        private static final String HEADER_LINE_DELIMITER = ":";

        private final Map<String, String> headers = new HashMap<>();

        public Builder addHeaderLine(String headerLine) {
            StringUtils.validateNonNullAndNotEmpty(() -> new InvalidHttpMessageException(headerLine), headerLine);

            String[] headerNameAndValue = parseHeaderNameAndValue(headerLine);
            this.headers.put(headerNameAndValue[0], headerNameAndValue[1]);

            return this;
        }

        private String[] parseHeaderNameAndValue(String headerLine) {
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

        public HttpHeader build() {
            return new HttpHeader(this.headers);
        }
    }
}
