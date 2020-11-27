package model.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import model.general.Header;
import model.general.Headers;
import model.general.Status;

public class HttpResponse {

    private final StatusLine statusLine;
    private final Headers headers;
    private final byte[] body;

    private HttpResponse(StatusLine statusLine, Headers headers, byte[] body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public static HttpResponse of(Status status) {
        return new HttpResponse(StatusLine.of(status), new Headers(), null);
    }

    public static HttpResponse of(StatusLine statusLine, Headers headers, byte[] body) {
        return new HttpResponse(statusLine, headers, body);
    }

    public static HttpResponse redirectResponse(String httpVersion, String location,
        String cookie) {
        StatusLine statusLine = StatusLine.of(httpVersion, Status.FOUND);
        Headers headers = new Headers();
        headers.addHeader(Header.LOCATION, location);
        headers.addHeader(Header.SET_COOKIE, cookie);

        return HttpResponse.of(statusLine, headers, null);
    }

    public void writeToOutputStream(DataOutputStream dataOutputStream)
        throws IOException {
        writeStatusLine(dataOutputStream);
        writeHeaders(dataOutputStream);
        writeBody(dataOutputStream);
    }

    private void writeStatusLine(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream
            .writeBytes(getHttpVersion() + " " + getStatusCode() + " " + getReasonPhrase()
                + System.lineSeparator());
    }

    private void writeHeaders(DataOutputStream dataOutputStream)
        throws IOException {
        for (Map.Entry<Header, String> entry : headers.getHeaders()
            .entrySet()) {
            dataOutputStream.writeBytes(entry.getKey().getName() + ": " + entry.getValue()
                + System.lineSeparator());
        }
        dataOutputStream.writeBytes(System.lineSeparator());
    }

    private void writeBody(DataOutputStream dataOutputStream) throws IOException {
        if (hasContents()) {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        }
    }

    private boolean hasContents() {
        return headers.hasKey(Header.CONTENT_LENGTH);
    }

    public String getHttpVersion() {
        return statusLine.getHttpVersion();
    }

    public String getStatusCode() {
        return statusLine.getStatusCode();
    }

    public String getReasonPhrase() {
        return statusLine.getReasonPhrase();
    }

    public Headers getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }
}
