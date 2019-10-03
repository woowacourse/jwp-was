package http.response;

import http.common.HttpCookie;
import http.common.HttpHeader;
import http.common.HttpVersion;
import http.common.SessionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String CONTENT_TYPE = "Content-Type: ";

    private StatusLine statusLine;
    private HttpHeader httpHeader;
    private HttpResponseBody httpResponseBody;
    private List<HttpCookie> httpCookies = new ArrayList<>();

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public HttpResponseBody getHttpResponseBody() {
        return httpResponseBody;
    }

    public List<HttpCookie> getHttpCookies() {
        return httpCookies;
    }

    public void forward(String url) {
        this.statusLine = new StatusLine(HttpStatus.OK, HttpVersion.HTTP_1_1);
        this.httpHeader = HttpHeader.of(Arrays.asList(CONTENT_TYPE + HttpContentType.of(url).getContentType()));

        try {
            this.httpResponseBody = HttpResponseBody.of(FileIoUtils.loadFileFromClasspath(url));
        } catch (IOException | URISyntaxException e) {
            logger.error("{}", e.getMessage());
        }

        logger.info("{}", this);
    }

    public void forward(byte[] body) {
        this.statusLine = new StatusLine(HttpStatus.OK, HttpVersion.HTTP_1_1);
        this.httpHeader = HttpHeader.of(Arrays.asList(CONTENT_TYPE + HttpContentType.HTML));
        this.httpResponseBody = HttpResponseBody.of(body);

        logger.info("{}", this);
    }

    public void redirect(String url) {
        this.statusLine = new StatusLine(HttpStatus.FOUND, HttpVersion.HTTP_1_1);
        this.httpHeader = HttpHeader.redirect(url);
        this.httpResponseBody = HttpResponseBody.empty();
    }

    public void setCookie(HttpCookie httpCookie) {
        httpCookies.add(httpCookie);
    }

    public void addJSessionId(String jSessionId) {
        if (jSessionId == null) {
            return;
        }

        setCookie(HttpCookie.builder(SessionPool.SESSION_ID, jSessionId).build());
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
