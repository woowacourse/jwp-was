package http.response;

import http.HttpHeader;
import http.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final DataOutputStream dos;
    private HttpResponseStartLine httpResponseStartLine;
    private HttpHeader header;
    private byte[] body;

    private HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public static HttpResponse of(DataOutputStream dos) {
        return new HttpResponse(dos);
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

    public void responseStartLine() throws IOException {
        logger.debug("{}", httpResponseStartLine.toString() + "\r\n" );
        String line = httpResponseStartLine.toString();
        dos.writeBytes(line + "\r\n");
    }

    public void responseHeader() {
        try {
            for (String line : header.getKeySet()) {
                String s = line + ": "+ header.getHeader(line);
                logger.debug("{}", line + ": "+ header.getHeader(line) + "\r\n");
                dos.writeBytes(line + ": "+ header.getHeader(line));
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            // TODO: 2019-09-20 여기에
        }
    }

    public void responseBody() {
        try {
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void forward() throws IOException {
        responseStartLine();
        responseHeader();
        responseBody();
    }

}
