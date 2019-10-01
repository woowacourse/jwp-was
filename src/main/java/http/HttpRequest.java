package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private RequestLine requestLine;

    private HttpHeaders headers;

    private RequestParams requestParams = new RequestParams();

    private Map<String, Object> attributes = new HashMap<>();

    public HttpRequest(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            requestLine = new RequestLine(createRequestLine(br));
            requestParams.addQueryString(requestLine.getQueryString());
            headers = processHeaders(br);
            requestParams.addBody(IOUtils.readData(br, headers.getContentLength()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String createRequestLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) {
            throw new IllegalStateException();
        }
        return line;
    }

    private HttpHeaders processHeaders(BufferedReader br) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String line;
        while (!(line = br.readLine()).equals("")) {
            headers.add(line);
        }
        return headers;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String name) {
        return headers.getHeader(name);
    }

    public String getParameter(String name) {
        return requestParams.getParameter(name);
    }

    public HttpCookie getCookies() {
        return headers.getCookies();
    }

    public HttpSession getSession() {
        return headers.getSession();
    }

    public void addAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
