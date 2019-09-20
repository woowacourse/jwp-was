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
    private HttpHeader header;
    private StatusCode code;

    private HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public static HttpResponse of(DataOutputStream dos) {
        return new HttpResponse(dos);
    }

    public void addHeader(List<String> headerLines) {
        header = HttpHeader.of(headerLines);
    }

    public void responseStartLine(String line) throws IOException {
        dos.writeBytes(line);
    }

    public void responseHeader() {
        try {
            for (String line : header.getKeySet()) {
                logger.debug("{}", line + ": "+ header.getHeader(line) + "\r\n");
                dos.writeBytes(line + ": "+ header.getHeader(line));
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            // TODO: 2019-09-20 여기에
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
