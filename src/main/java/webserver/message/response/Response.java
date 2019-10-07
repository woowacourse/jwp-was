package webserver.message.response;

import java.nio.ByteBuffer;

public class Response {
    private static final byte[] HEADER_BODY_DELIMITER_BYTES = "\r\n\r\n".getBytes();

    private final ResponseStatusLine statusLine;
    private final ResponseHeader header;
    private final ResponseBody body;

    Response(final ResponseStatusLine statusLine, final ResponseHeader header, final ResponseBody body) {
        this.statusLine = statusLine;
        this.header = header;
        this.body = body;
    }

    public byte[] toBytes() {
        final int bodyLength = this.body.length();
        final byte[] line = this.statusLine.toBytes();
        final byte[] header = this.header.toBytes(bodyLength);

        return ByteBuffer
                .allocate(line.length + header.length + HEADER_BODY_DELIMITER_BYTES.length + bodyLength)
                .put(line)
                .put(header)
                .put(HEADER_BODY_DELIMITER_BYTES)
                .put(this.body.getBody())
                .array();
    }
}
