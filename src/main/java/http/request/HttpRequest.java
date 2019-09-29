package http.request;

import http.HTTP;
import http.RequestMethod;
import session.HttpSession;
import session.HttpSessionManager;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static session.HttpSession.SESSION_ID;

public class HttpRequest implements AutoCloseable {
    private static final String EMPTY_STRING = "";
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    private BufferedReader bufferedReader;

    public HttpRequest(InputStream in) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.requestLine = new RequestLine(bufferedReader.readLine());

        this.requestHeader = new RequestHeader(bufferedReader);

        if (requestHeader.contains(HTTP.CONTENT_LENGTH)) {
            this.requestBody = new RequestBody(bufferedReader,
                    Integer.parseInt(requestHeader.getHeaderContents(HTTP.CONTENT_LENGTH.getPhrase())));
        }
    }

    public String getHeaderContents(HTTP http) {
        return requestHeader.getHeaderContents(http.getPhrase());
    }

    public String getCookieValue(String name) {
        return requestHeader.getCookieValue(name);
    }

    public boolean checkMethod(RequestMethod requestMethod) {
        return requestLine.getMethod().equals(requestMethod);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getQueryString() {
        return decode(requestLine.getQuery());
    }

    public String getBody() {
        if (requestBody == null) {
            return EMPTY_STRING;
        }
        return decode(requestBody.getBody());
    }

    private String decode(String str) {
        try {
            return URLDecoder.decode(str, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported");
        }
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }

    public HttpSession getSession(HttpSessionManager httpSessionManager) {
        String sessionId = this.getCookieValue(SESSION_ID);
        return httpSessionManager.findSession(sessionId);
    }
}
