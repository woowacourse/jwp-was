package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private ResponseStatusLine statusLine;
    private ResponseHeader header;
    private ResponseBody body;

    public HttpResponse(HttpRequest httpRequest) {
        this.header = ResponseHeader.of();
        this.body = ResponseBody.of(httpRequest);
    }

    public boolean addBody(byte[] body) {
        return this.body.addBody(body);
    }

    public void addHeader(HttpRequest httpRequest, String statusCode, String statusText) {
        statusLine = ResponseStatusLine.of(httpRequest, statusCode, statusText);
    }

    public void send(DataOutputStream dos) throws IOException {
        dos.writeBytes(statusLine.response());
        dos.writeBytes(header.response("Content-Type"));
        dos.writeBytes("Content-Length: " + body.getLengthOfContent() + "\r\n");
        dos.writeBytes("\r\n");
        try {
            dos.write(body.getContent(), 0, body.getLengthOfContent());
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
