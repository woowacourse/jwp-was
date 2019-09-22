package http.response;

import http.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private HttpResponseStartLine httpResponseStartLine;
    private HttpHeader header;
    private byte[] body;

    public HttpResponse() {
    }

    public void addHeader(List<String> headerLines) {
        header = HttpHeader.of(headerLines);
    }

    public void okResponse(byte[] body) {
        this.httpResponseStartLine = new HttpResponseStartLine(StatusCode.OK, "HTTP/1.1");
        this.body = body;
    }

    public void redirectResponse() {
        this.httpResponseStartLine = new HttpResponseStartLine(StatusCode.FOUND, "HTTP/1.1");
        this.body = new byte[]{};
    }

    private void writeStartLine(DataOutputStream dos) throws IOException {
        logger.debug("{}", httpResponseStartLine.toString() + "\r\n");
        String line = httpResponseStartLine.toString();
        dos.writeBytes(line + "\r\n");
    }

    private void writeHeader(DataOutputStream dos) {
        try {
            for (String line : header.getKeySet()) {
                logger.debug("{}", line + ": " + header.getHeader(line) + "\r\n");
                dos.writeBytes(line + ": " + header.getHeader(line));
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeBody(DataOutputStream dos) {
        try {
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void forward(DataOutputStream dos) throws IOException {
        writeStartLine(dos);
        writeHeader(dos);
        writeBody(dos);
    }

}
