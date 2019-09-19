package webserver;

import java.util.Arrays;

public class HttpResponse {
    private final StatusLine statusLine;
    private final HttpHeader httpHeader;
    private final byte[] body;

    public HttpResponse(StatusLine statusLine, HttpHeader httpHeader, byte[] body) {
        this.statusLine = statusLine;
        this.httpHeader = httpHeader;
        this.body = body;
    }

    public static HttpResponse found(String location) {
        StatusLine statusLine = new StatusLine("HTTP/1.1", ResponseStatus.FOUND);
        HttpHeader httpHeader = new HttpHeader(Arrays.asList("Location: " + location));
        return new HttpResponse(statusLine, httpHeader, new byte[0]);
    }

    public static HttpResponse ok(byte[] body, String extension) {
        StatusLine statusLine = new StatusLine("HTTP/1.1", ResponseStatus.OK);
        HttpHeader httpHeader = new HttpHeader(Arrays.asList(
                "Content-Type: " + ContentType.getContentType(extension) + ";charset=utf-8",
                "Content-Length: " + body.length
        ));
        return new HttpResponse(statusLine, httpHeader, body);
    }

    public byte[] serialize() {
        String responseHeader = String.format("%s\r\n%s",
                statusLine.serialize(),
                httpHeader.serialize());

        if (body != null) {
            responseHeader += "\r\n" + new String(body);
        }

        return responseHeader.getBytes();
    }
}
