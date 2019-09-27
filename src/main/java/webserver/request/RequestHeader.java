package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestHeader {
    private static final String HEADER_FIELD_CONTENT_LENGTH = "Content-Length";
    private static final String HEADER_FIELD_SEPARATOR = ": ";
    private static final int INIT_CONTENT_LENGTH = 0;

    private final Map<String, String> headerFields;

    private RequestHeader(Map<String, String> headerFields) {
        this.headerFields = headerFields;
    }

    public static RequestHeader of(BufferedReader br) throws IOException {
        Map<String, String> headerFields = new HashMap<>();
        String line = br.readLine();

        while (!"".equals(line) && line != null) {
            String[] headerField = line.split(HEADER_FIELD_SEPARATOR);
            headerFields.put(headerField[0], headerField[1]);
            line = br.readLine();
        }
        return new RequestHeader(headerFields);
    }

    public String getHeaderFieldValue(String fieldName) {
        if (headerFields.containsKey(fieldName)) {
            return headerFields.get(fieldName);
        }
        throw new IllegalArgumentException("Not Found Header Attribute");
    }

    public int getContentLength() {
        if (headerFields.containsKey(HEADER_FIELD_CONTENT_LENGTH)) {
            return Integer.parseInt(headerFields.get(HEADER_FIELD_CONTENT_LENGTH));
        }
        return INIT_CONTENT_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestHeader that = (RequestHeader) o;
        return Objects.equals(headerFields, that.headerFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headerFields);
    }
}
