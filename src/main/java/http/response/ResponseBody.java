package http.response;

import http.MediaType;

public class ResponseBody {
    private byte[] content;
    private MediaType mediaType;

    public ResponseBody(byte[] content, MediaType mediaType) {
        this.content = content;
        this.mediaType = mediaType;
    }

    public byte[] getContent() {
        return content;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}
