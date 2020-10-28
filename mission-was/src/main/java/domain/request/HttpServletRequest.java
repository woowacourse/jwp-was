package domain.request;

import java.io.BufferedReader;
import java.io.IOException;

import exception.CookieNotFoundException;
import servlet.Cookie;
import servlet.HttpRequest;
import servlet.HttpSession;
import servlet.RequestMethod;
import servlet.StaticFileType;
import utils.IOUtils;

public class HttpServletRequest implements HttpRequest {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String COLON = ": ";
    private static final String JSSESION_ID = "JSESSIONID";
    private static final String EMPTY_STRING = "";

    private final RequestLine requestLine;
    private final HeaderParams headerParams;
    private final RequestParams requestParams;
    private final Cookies cookies;

    public HttpServletRequest(BufferedReader bufferedReader) throws IOException {
        String header = IOUtils.readHeader(bufferedReader);
        String[] lines = header.split(NEW_LINE);
        this.requestLine = new RequestLine(lines[0]);
        this.headerParams = new HeaderParams();
        for (int i = 1; i < lines.length; i++) {
            String[] keyValue = lines[i].split(COLON);
            headerParams.put(keyValue[0], keyValue[1]);
        }

        String params = this.requestLine.getQuery();
        if (this.requestLine.isPostMethod()) {
            params = IOUtils.readBody(bufferedReader, findContentLength());
        }
        this.requestParams = new RequestParams(params);
        this.cookies = Cookies.from(getHeader("Cookie"));
    }

    @Override
    public RequestMethod getMethod() {
        return requestLine.getRequestMethod();
    }

    @Override
    public String getHeader(String headerName) {
        return headerParams.get(headerName);
    }

    @Override
    public String getParameter(String key) {
        return requestParams.get(key);
    }

    @Override
    public boolean hasPathOfStaticFile() {
        return this.requestLine.hasPathOfStaticFile();
    }

    @Override
    public RequestMethod getRequestMethod() {
        return requestLine.getRequestMethod();
    }

    @Override
    public String getPath() {
        return requestLine.getPath();
    }

    @Override
    public StaticFileType findExtension() {
        return requestLine.findExtension();
    }

    @Override
    public int findContentLength() {
        String length = headerParams.getOrDefault("Content-Length", "0");
        return Integer.parseInt(length);
    }

    @Override
    public HttpSession getSession() {
        HttpSessionStorage httpSessionStorage = HttpSessionStorage.getInstance();
        try {
            Cookie cookie = cookies.findCookie(JSSESION_ID);
            return httpSessionStorage.getSession(cookie.getValue());
        } catch (CookieNotFoundException e) {
            return httpSessionStorage.getSession(EMPTY_STRING);
        }
    }
}
