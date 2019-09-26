package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.http.httpRequest.HttpRequestBody;
import webserver.http.httpRequest.HttpRequestHeader;
import webserver.http.httpRequest.HttpStartLine;

import java.io.*;
import java.net.URLDecoder;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private static final String TAG = "HttpRequest";

    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String JSESSION_ID = "JSESSIONID";

    private HttpStartLine httpStartLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(HttpStartLine httpStartLine, HttpRequestHeader httpRequestHeader, HttpRequestBody httpRequestBody) {
        this.httpStartLine = httpStartLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public static HttpRequest create(InputStream in) {
        BufferedReader br = getBufferedReader(in);

        String startLine = parseStartLine(br);
        String header = parseHeader(br);
        HttpStartLine httpStartLine = HttpStartLine.create(startLine);
        HttpRequestHeader httpRequestHeader = HttpRequestHeader.create(header);
        HttpRequestBody httpRequestBody = getHttpRequestBody(br, httpStartLine, httpRequestHeader);

        return new HttpRequest(httpStartLine, httpRequestHeader, httpRequestBody);
    }

    private static BufferedReader getBufferedReader(InputStream in) {
        try {
            return new BufferedReader(new InputStreamReader(in, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("{} 클래스 에러 발생", TAG);
            throw new IllegalArgumentException("올바르지 않은 요청입니다.");
        }
    }

    private static String parseStartLine(BufferedReader br) {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException("올바르지 않은 입력 StartLine 입니다.");
        }
    }

    private static String parseHeader(BufferedReader br) {
        StringBuilder sb = new StringBuilder();
        String headerLine = null;
        try {
            headerLine = br.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException("올바르지 않은 입력 StartLine 입니다.");
        }

        while (!"".equals(headerLine)) {
            sb.append(headerLine);
            sb.append("\n");
            try {
                headerLine = br.readLine();
            } catch (IOException e) {
                throw new IllegalArgumentException("올바르지 않은 입력 StartLine 입니다.");
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    private static HttpRequestBody getHttpRequestBody(BufferedReader br, HttpStartLine httpStartLine, HttpRequestHeader httpRequestHeader) {
        if (httpStartLine.checkMethod(HttpMethod.GET)) {
            return HttpRequestBody.empty();
        }

        String body = parseBody(br, httpRequestHeader.getContentLength());
        return HttpRequestBody.create(body);
    }

    private static String parseBody(BufferedReader br, int contentLength) {
        String body = readBody(br, contentLength);
        return decodeBody(body);
    }

    private static String readBody(BufferedReader br, int contentLength) {
        try {
            return IOUtils.readData(br, contentLength);
        } catch (IOException e) {
            log.error("{} 클래스 {} 메서드 오류", TAG, "parseBody");
            throw new IllegalArgumentException();
        }
    }

    private static String decodeBody(String body) {
        try {
            String decode = URLDecoder.decode(body, "UTF-8");
            return decode;
        } catch (UnsupportedEncodingException e) {
            log.error("{} 클래스 {} 메서드 오류", TAG, "decodeBody");
            throw new IllegalArgumentException();
        }
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
