package model.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import model.general.Header;
import model.general.Status;

public class HttpResponse {

    private final StatusLine statusLine;
    private final Map<Header, String> headers;
    private final byte[] body;

    private HttpResponse(StatusLine statusLine, Map<Header, String> headers, byte[] body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public static HttpResponse of(Status status) {
        return new HttpResponse(StatusLine.of(status), Collections.emptyMap(), null);
    }

    public static HttpResponse of(StatusLine statusLine, Map<Header, String> headers, byte[] body) {
        return new HttpResponse(statusLine, headers, body);
    }

    public void writeToOutputStream(DataOutputStream dataOutputStream)
        throws IOException {
        writeStatusLine(dataOutputStream);
        writeHeaders(dataOutputStream);
        writeBody(dataOutputStream);
    }

    private void writeStatusLine(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes(
            getHttpVersion() + " " + getStatusCode() + " " + getReasonPhrase() + "\r\n");
    }

    private void writeHeaders(DataOutputStream dataOutputStream)
        throws IOException {
        Map<Header, String> headers = getHeaders();

        for (Map.Entry<Header, String> entry : headers.entrySet()) {
            dataOutputStream
                .writeBytes(entry.getKey().getName() + ": " + entry.getValue() + "\r\n");
        }
        dataOutputStream.writeBytes("\r\n");
    }

    private void writeBody(DataOutputStream dataOutputStream) throws IOException {
        if (hasContents()) {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        }
    }

    private boolean hasContents() {
        return headers.containsKey(Header.CONTENT_LENGTH);
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

    public Map<Header, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }
}
