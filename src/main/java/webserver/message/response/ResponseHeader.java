package webserver.message.response;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseHeader {
    private static final String NEW_LINE = "\r\n";
    private static final String EMPTY = "";

    private final Map<String, String> responseFields;

    ResponseHeader(final Map<String, String> responseFields) {
        this.responseFields = responseFields;
    }

    public Map<String, String> getResponseFields() {
        return Collections.unmodifiableMap(this.responseFields);
    }

    public String getFieldValue(final String fieldKey) {
        return this.responseFields.getOrDefault(fieldKey, EMPTY);
    }

    public byte[] toBytes(final int contentLength) {
        this.responseFields.put("Content-Length", Integer.toString(contentLength));

        return this.responseFields.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(NEW_LINE))
                .getBytes();
    }
}
