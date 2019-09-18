package webserver;

import java.util.Map;
import java.util.Set;

public class Response {

    private final Status status;
    private final MediaType mediaType;
    private final Map<String, String> headers;
    private final byte[] body;

    private Response(Status status, MediaType mediaType, Map<String, String> headers, byte[] body) {
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

    public static final class ResponseBuilder {
        private Status status;
        private MediaType mediaType;
        private Map<String, String> headers;
        private byte[] body;

        private ResponseBuilder() {
        }

        public static ResponseBuilder createBuilder() {
            return new ResponseBuilder();
        }

        public ResponseBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public ResponseBuilder withMediaType(MediaType mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        public ResponseBuilder withHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public ResponseBuilder withBody(byte[] body) {
            this.body = body;
            return this;
        }

        public Response build() {
            return new Response(status, mediaType, headers, body);
        }
    }
}
