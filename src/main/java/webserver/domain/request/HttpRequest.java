package webserver.domain.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private static final String lineSeparator = System.getProperty("line.separator");
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestLine requestLine;
    private final String header;
    private final String body;

    public HttpRequest(RequestLine requestLine, String header, String body) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public static HttpRequest of(BufferedReader br) throws IOException {
        if (!br.ready()) {
            throw new RuntimeException("잘못된 HTTP 요청입니다. 요청라인이 존재하지 않습니다.");
        }
        String line = br.readLine();
        RequestLine requestLine = RequestLine.of(line);
        logger.debug("Request Line{}{}{}", lineSeparator, line, lineSeparator);

        if (!br.ready()) {
            throw new RuntimeException("잘못된 HTTP 요청입니다. 헤더가 존재하지 않습니다.");
        }
        StringBuilder header = new StringBuilder();
        do {
            line = br.readLine();
            if (line == null) {
                break;
            }
            header.append(line);
            header.append(lineSeparator);
        } while (!line.equals(""));
        logger.debug("Header{}{}", lineSeparator, header);

        if (!br.ready()) {
            return new HttpRequest(requestLine, header.toString(), "");
        }
        StringBuilder body = new StringBuilder();
        do {
            line = br.readLine();
            if (line == null) {
                break;
            }
            body.append(line);
            body.append(lineSeparator);
        } while (!line.equals(""));
        logger.debug("Body{}{}", lineSeparator, body);

        return new HttpRequest(requestLine, header.toString(), body.toString());
    }

    public String getPath() {
        if (requestLine.isTemplatesResource()) {
            return String.format("./templates%s", requestLine.getPath());
        }

        if (requestLine.isStaticResource()) {
            return String.format("./static%s", requestLine.getPath());
        }

        return requestLine.getPath();
    }

    public Map<String, String> getParameters() {
        return requestLine.getParameters();
    }

    public String getBody() {
        return body;
    }

    public boolean isForStaticContent() {
        return requestLine.isTemplatesResource() || requestLine.isStaticResource();
    }

    public boolean isForDynamicContent() {
        return !isForStaticContent();
    }
}
