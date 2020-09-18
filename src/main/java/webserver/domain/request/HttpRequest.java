package webserver.domain.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.domain.Header;

public class HttpRequest {
    private static final String lineSeparator = System.getProperty("line.separator");
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestLine requestLine;
    private final Header header;
    private final String body;

    public HttpRequest(RequestLine requestLine, Header header, String body) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public HttpRequest(RequestLine requestLine, Header header) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = "";
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
        Map<String, String> headerFields = new HashMap<>();
        while (!line.equals("")) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            String[] headerTokens = line.split(": ");
            if (headerTokens.length >=2) {
                headerFields.put(headerTokens[0], headerTokens[1]);
            }
            logger.debug("Header : {}", line);
        }

        logger.debug("Content-Length : {}", headerFields.get("Content-Length"));

        // TODO: 2020/09/18 body 추출하는 데 IOUtils.readData 활용
        // if (!br.ready()) {
        //     return new HttpRequest(requestLine, new Header(headerFields));
        // }
        // StringBuilder body = new StringBuilder();
        //     line = br.readLine();
        // do {
        //     line = br.readLine();
        //     if (line == null) {
        //         break;
        //     }
        //     body.append(line);
        //     body.append(lineSeparator);
        // } while (!line.equals(""));
        // if (body.substring(body.length() - lineSeparator.length(), body.length()).equals(lineSeparator)) {
        //     body.delete(body.length() - lineSeparator.length(), body.length());
        // }
        // logger.debug("Body{}{}", lineSeparator, body);
        //
        return new HttpRequest(requestLine, new Header(headerFields), "");
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

    public boolean isGet() {
        return requestLine.getMethod() == Method.GET;
    }

    public boolean isPost() {
        return requestLine.getMethod() == Method.POST;
    }
}
