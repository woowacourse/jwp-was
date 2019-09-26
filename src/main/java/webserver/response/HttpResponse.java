package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private static final String HEADER_FIELD_SEPARATOR = ": ";

    private ResponseStatusLine statusLine;
    private ResponseHeader header;
    private ResponseBody body;

    public HttpResponse() {
        this.header = new ResponseHeader();
        this.body = new ResponseBody();
    }

    public boolean addBody(byte[] body) {
        return this.body.addBody(body);
    }

    public void addStatusLine(HttpRequest httpRequest, String statusCode, String statusText) {
        statusLine = ResponseStatusLine.of(httpRequest, statusCode, statusText);
    }

    public boolean addHeader(String key, String value) {
        return header.addAttribute(key, value);
    }

    public void render(DataOutputStream dos) throws IOException {
        renderStatusLine(dos);
        renderHeader(dos);
        renderBody(dos);
    }

    private void renderStatusLine(DataOutputStream dos) throws IOException {
        dos.writeBytes(statusLine.response());
    }

    private void renderHeader(DataOutputStream dos) throws IOException {
        Map<String, String> attributes = header.getAttributes();
        for (String attributeName : attributes.keySet()) {
            dos.writeBytes(attributeName + HEADER_FIELD_SEPARATOR + attributes.get(attributeName) + "\r\n");
        }
    }

    private void renderBody(DataOutputStream dos) throws IOException {
        if (body.isEmpty()) {
            return;
        }
        dos.writeBytes("\r\n");
        dos.write(body.getContent(), 0, body.getLengthOfContent());
        dos.flush();
    }
}
