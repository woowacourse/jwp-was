package http.response;

import http.common.HttpCookie;
import http.common.HttpHeader;
import http.common.HttpVersion;
import http.common.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String CONTENT_TYPE = "Content-Type: ";
    private static final String JSESSIONID_COOKIE_PATH = "/";

    private StatusLine statusLine;
    private HttpHeader httpHeader;
    private HttpResponseBody httpResponseBody;
    private List<HttpCookie> httpCookies = new ArrayList<>();

    public HttpResponse() {
        this.statusLine = new StatusLine();
        this.httpHeader = HttpHeader.init();
        this.httpResponseBody = new HttpResponseBody();
    }

    public HttpStatus getHttpStatus() {
        return statusLine.getHttpStatus();
    }

    public HttpVersion getHttpVersion() {
        return statusLine.getHttpVersion();
    }

    public String getHeader(String key) {
        return httpHeader.get(key);
    }

    public Set<Map.Entry<String, String>> getHeaders() {
        return httpHeader.getHeaders().entrySet();
    }

    public HttpResponseBody getHttpResponseBody() {
        return httpResponseBody;
    }

    public List<HttpCookie> getHttpCookies() {
        return httpCookies;
    }

    public void forward(String url) {
        statusLine.setOk(HttpVersion.HTTP_1_1);
        httpHeader = HttpHeader.of(Arrays.asList(CONTENT_TYPE + HttpContentType.of(url).getContentType()));

        try {
            httpResponseBody.setBody(FileIoUtils.loadFileFromClasspath(url));
        } catch (IOException | URISyntaxException e) {
            logger.error("{}", e.getMessage());
        }

        logger.info("{}", this);
    }

    public void forward(byte[] body) {
        statusLine.setOk(HttpVersion.HTTP_1_1);
        httpHeader = HttpHeader.of(Arrays.asList(CONTENT_TYPE + HttpContentType.HTML));
        httpResponseBody.setBody(body);

        logger.info("{}", this);
    }

    public void redirect(String url) {
        statusLine.setRedirect(HttpVersion.HTTP_1_1);
        httpHeader.redirect(url);
    }

    public void setCookie(HttpCookie httpCookie) {
        httpCookies.add(httpCookie);
    }

    public void addJSessionId(String jSessionId) {
        if (jSessionId == null) {
            return;
        }

        HttpCookie jSessionIdCookie = HttpCookie.builder(SessionManager.SESSION_ID, jSessionId)
                .path(JSESSIONID_COOKIE_PATH)
                .build();
        setCookie(jSessionIdCookie);
        logger.info("jSessionIdCookie: {}", jSessionIdCookie);
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "statusLine=" + statusLine +
                ", httpHeader=" + httpHeader +
                ", httpResponseBody=" + httpResponseBody +
                ", httpCookies=" + httpCookies +
                '}';
    }
}
