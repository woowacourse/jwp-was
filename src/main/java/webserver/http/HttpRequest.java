package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.http.httpRequest.HttpRequestBody;
import webserver.http.httpRequest.HttpRequestHeader;
import webserver.http.httpRequest.HttpStartLine;

import java.io.*;
import java.net.URLDecoder;

import static webserver.http.httpRequest.HttpRequestHeader.HEADER_LINE_SEPARATOR;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private static final String TAG = "HttpRequest";

    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String JSESSION_ID = "JSESSIONID";
    public static final String UTF_8 = "UTF-8";

    private HttpStartLine httpStartLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(HttpStartLine httpStartLine, HttpRequestHeader httpRequestHeader, HttpRequestBody httpRequestBody) {
        this.httpStartLine = httpStartLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public static HttpRequest create(InputStream in) throws IOException {
        BufferedReader br = getBufferedReader(in);
        String startLine = parseStartLine(br);
        String header = parseHeader(br);

        HttpStartLine httpStartLine = HttpStartLine.create(startLine);
        HttpRequestHeader httpRequestHeader = HttpRequestHeader.create(header);
        HttpRequestBody httpRequestBody = getHttpRequestBody(br, httpStartLine, httpRequestHeader);

        return new HttpRequest(httpStartLine, httpRequestHeader, httpRequestBody);
    }

    private static BufferedReader getBufferedReader(InputStream in) throws UnsupportedEncodingException {
        return new BufferedReader(new InputStreamReader(in, UTF_8));
    }

    private static String parseStartLine(BufferedReader br) throws IOException {
        String startLine = br.readLine();
        log.debug("startLine : {}", startLine);
        return startLine;
    }

    private static String parseHeader(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String headerLine = br.readLine();

        while (!"".equals(headerLine)) {
            sb.append(headerLine);
            sb.append(HEADER_LINE_SEPARATOR);
            headerLine = br.readLine();
        }
        sb.append(HEADER_LINE_SEPARATOR);
        String header = sb.toString();
        log.debug("header : {}", header);
        return header;
    }

    private static HttpRequestBody getHttpRequestBody(BufferedReader br, HttpStartLine httpStartLine, HttpRequestHeader httpRequestHeader) throws IOException {
        if (httpStartLine.checkMethod(HttpMethod.GET)) {
            return HttpRequestBody.empty();
        }

        String body = parseBody(br, httpRequestHeader.getContentLength());
        return HttpRequestBody.create(body);
    }

    private static String parseBody(BufferedReader br, int contentLength) throws IOException {
        String body = readBody(br, contentLength);
        String decodedBody = decodeBody(body);
        log.debug("decodedBody : {}", decodedBody);
        return decodedBody;
    }

    private static String readBody(BufferedReader br, int contentLength) throws IOException {
        return IOUtils.readData(br, contentLength);
    }

    private static String decodeBody(String body) throws UnsupportedEncodingException {
        return URLDecoder.decode(body, UTF_8);
    }

    public boolean checkMethod(HttpMethod httpMethod) {
        return httpStartLine.checkMethod(httpMethod);
    }

    public String getPath() {
        return httpStartLine.getPath();
    }

    public String getParam(String key) {
        String param = httpRequestBody.getBodyParam(key);
        if (param == null) {
            param = httpStartLine.getParam(key);
        }
        return param;
    }

    public String getCookie() {
        return httpRequestHeader.getCookie();
    }

    public String getSessionId() {
        return httpRequestHeader.getSessionId();
    }
}
