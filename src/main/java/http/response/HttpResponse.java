package http.response;

import http.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final IDataOutputStream dos;

    private StatusCode code;
    private final HttpResponseHeader httpResponseHeader;

    private HttpResponse(IDataOutputStream dos) {
        this.dos = dos;

        httpResponseHeader = HttpResponseHeader.create();
    }

    public static HttpResponse of(IDataOutputStream dos) {
        return new HttpResponse(dos);
    }

    public static HttpResponse ok(IDataOutputStream dos) {
        // code = StatusCode.Ok;
        return new HttpResponse(dos);
    }

    //  public void response200Header(int lengthOfBodyContent, String contentType) {

    public void response200Header() {
        try {
            List<String> lines = Arrays.asList(
                    String.format("HTTP/1.1 %d %s \r\n", 200, "OK"),
                    toHeaderString());

            for (String line : lines) {
                dos.writeBytes(line);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302Header() {
        try {
            dos.writeBytes("HTTP/1.1 302 Found\r\n" + toHeaderString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHeader(String key, String toHeaderValue) {
        httpResponseHeader.setHeader(key, toHeaderValue);
    }

    private String toHeaderString() {
        StringBuilder sb = new StringBuilder();
        for (String key : httpResponseHeader.keySet()) {
            sb.append(String.format("%s: %s\r\n", key, httpResponseHeader.getHeader(key)));
        }
        sb.append("\r\n");
        return sb.toString();
    }
}
