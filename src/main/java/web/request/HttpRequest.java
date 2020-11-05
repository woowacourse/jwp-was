package web.request;

import utils.IOUtils;
import web.HttpHeader;
import web.HttpSession;
import web.HttpSessions;
import web.cookie.HttpCookies;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private final RequestUri requestUri;
    private final HttpHeader httpHeader;
    private RequestBody requestBody;

    public HttpRequest(final BufferedReader bufferedReader) throws IOException {
        this.requestUri = new RequestUri(bufferedReader.readLine());
        this.httpHeader = HttpHeader.ofRequest(IOUtils.readHeader(bufferedReader));
        if (HttpMethod.POST == getMethod()) {
            this.requestBody = new RequestBody(IOUtils.readData(bufferedReader, httpHeader.getContentLength()));
        }
    }

    public String getRequestPath() {
        return requestUri.getRequestPath();
    }

    public String getParam(final String key) {
        return requestUri.getParam(key);
    }

    public int getContentLength() {
        return httpHeader.getContentLength();
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public HttpMethod getMethod() {
        return requestUri.getMethod();
    }

    public boolean isGetMethod() {
        return getMethod().isGet();
    }

    public HttpHeader getHeader() {
        return httpHeader;
    }

    public HttpCookies getCookies() {
        return this.httpHeader.getCookies();
    }

    public HttpSession getSession() {
        return HttpSessions.get(httpHeader.getCookies().get("JSESSIONID"));
    }
}
