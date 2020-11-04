package web.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.IOUtils;
import web.session.HttpSession;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private HttpRequestLine httpRequestLine;
    private HttpRequestHeaders httpRequestHeaders;
    private HttpRequestParams httpRequestParams = new HttpRequestParams();

    public HttpRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            httpRequestLine = HttpRequestLine.from(br.readLine());
            httpRequestParams.addQueryString(httpRequestLine.getQueryString());
            httpRequestHeaders = HttpRequestHeaders.from(br);
            httpRequestParams.addBody(IOUtils.readData(br, httpRequestHeaders.getContentLength()));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public HttpRequestParams getRequestBody() {
        return httpRequestParams;
    }

    public String getParameter(String name) {
        return httpRequestParams.getParameter(name);
    }

    public HttpMethod getMethod() {
        return httpRequestLine.getMethod();
    }

    public String getPath() {
        return httpRequestLine.getPath();
    }

    public String getHeader(String key) {
        return httpRequestHeaders.getParams().get(key);
    }

    public HttpCookie getCookies() {
        return httpRequestHeaders.getCookies();
    }

    public HttpSession getSession() {
        return httpRequestHeaders.getSession();
    }
}
