package server.web.request;

import server.utils.IOUtils;
import server.web.HttpHeader;
import server.web.HttpSession;
import server.web.HttpSessions;

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

    public HttpSession getSession() {
        return HttpSessions.get(httpHeader.getSessionId());
    }

    public String getSessionId() {
        return this.getSession().getId();
    }
}
