package webserver;

import org.springframework.util.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private static final String METHOD = "method";
    private static final String CONTENT_LENGTH = "Content-length";
    private static final String BLANK = "";
    private final HttpHeader httpHeader;
    private final String body;

    public HttpRequest(BufferedReader br) throws IOException {
        httpHeader = extractHeader(br);
        body = getBody(br, httpHeader);
    }

    private String getBody(BufferedReader br, HttpHeader httpHeader) throws IOException {
        HttpMethod requestMethod = HttpMethod.valueOf(httpHeader.get(METHOD));
        if(requestMethod.hasBody()) {
            return extractBody(br, httpHeader.get(CONTENT_LENGTH));
        }
        return BLANK;
    }

    private String extractBody(BufferedReader br, String size) throws IOException {
        return IOUtils.readData(br, Integer.parseInt(size));
    }

    private HttpHeader extractHeader(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();

        String line = br.readLine();
        while (!StringUtils.isEmpty(line)) {
            lines.add(line);
            line = br.readLine();
        }

        return new HttpHeader(lines);
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public String getBody() {
        return body;
    }

    public String getUrl() {
        return httpHeader.get("url");
    }
}
