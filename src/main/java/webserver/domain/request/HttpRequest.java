package webserver.domain.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.IOUtils;
import webserver.domain.Header;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestLine requestLine;
    private final Header header;
    private final String body;

    private HttpRequest(RequestLine requestLine, Header header, String body) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public static HttpRequest of(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line = br.readLine();
        RequestLine requestLine = RequestLine.of(line);
        logger.debug("Request Line : {}", line);

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
        Header header = new Header(headerFields);

        String contentLength = headerFields.get("Content-Length");
        if (Objects.isNull(contentLength)) {
            return new HttpRequest(requestLine, header, "");
        }
        String body = IOUtils.readData(br, Integer.parseInt(contentLength));
        logger.debug("Body : {}", body);

        return new HttpRequest(requestLine, header, body);
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
