package webserver;

import java.util.Map;
import java.util.Set;

public class Response {

    private final Status status;
    private final MediaType mediaType;
    private final Map<String, String> headers;
    private final byte[] body;

    public Response(Status status, MediaType mediaType, Map<String, String> headers, byte[] body) {
        this.status = status;
        this.mediaType = mediaType;
        this.headers = headers;
        this.body = body;
    }

    public Set<String> getHeaderKeys() {
        return headers.keySet();
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public Status getStatus() {
        return status;
    }

    public String getMediaType() {
        if (mediaType == null) {
            return null;
        }
        return mediaType.getContentType();
    }

    public byte[] getBody() {
        return body;
    }
}
