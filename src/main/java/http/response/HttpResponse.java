package http.response;

import http.Cookie;
import http.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.page.Page;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HttpResponse implements HttpResponseAccessor {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final IDataOutputStream dos;
    private final HttpResponseHeader httpResponseHeader;

    private HttpResponse(IDataOutputStream dos) {
        this.dos = dos;

        httpResponseHeader = HttpResponseHeader.create();
    }

    public static HttpResponse of(IDataOutputStream dos) {
        return new HttpResponse(dos);
    }

    public void forward(Page page) {
        byte[] body = page.getBody();

        setHeader("Content-Type", page.getContentType().toHeaderValue());
        setHeader("Content-Length", Integer.toString(body.length));

        responseHeader(StatusCode.OK);
        responseBody(body);
    }

    public void redirect(String location) {
        clear();
        setHeader("Location", location);
        responseHeader(StatusCode.Found);
    }

    public void responseHeader(StatusCode statusCode) {
        try {
            List<String> lines = Arrays.asList(
                    String.format("HTTP/1.1 %d %s \r\n", statusCode.getValue(), statusCode.name()),
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

    @Override
    public void setCookie(Cookie cookie) {
        setHeader("Set-Cookie", cookie.toHeaderValue());
    }

    public void clear() {
        httpResponseHeader.clear();
    }
}
