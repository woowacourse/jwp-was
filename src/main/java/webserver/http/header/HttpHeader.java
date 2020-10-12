package webserver.http.header;

import exception.InvalidHttpMessageException;
import utils.StringUtils;

public class HttpHeader {
    private static final String HEADER_LINE_DELIMITER = ":";

    private final String name;
    private final String value;

    public HttpHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static HttpHeader from(String header) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidHttpMessageException(header), header);

        String[] headerNameAndValue = parseHeaderNameAndValue(header);

        return of(headerNameAndValue[0], headerNameAndValue[1]);
    }

    public static HttpHeader of(String name, String value) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidHttpMessageException(name, value), name, value);

        String headerName = name.trim();
        String headerValue = value.trim();

        if (headerName.isEmpty() || headerValue.isEmpty()) {
            throw new InvalidHttpMessageException(headerName, headerValue);
        }

        return new HttpHeader(headerName, headerValue);
    }

    private static String[] parseHeaderNameAndValue(String headerLine) {
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

    public boolean hasSameName(String name) {
        return this.name.equals(name);
    }

    public String getValue() {
        return value;
    }

    public String toHttpMessage() {
        return this.name + HEADER_LINE_DELIMITER + " " + this.value;
    }
}
