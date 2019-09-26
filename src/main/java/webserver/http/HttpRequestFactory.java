package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.http.httpRequest.HttpRequestBody;
import webserver.http.httpRequest.HttpRequestHeader;
import webserver.http.httpRequest.HttpStartLine;

import java.io.*;
import java.net.URLDecoder;

public class HttpRequestFactory {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestFactory.class);
    private static final String TAG = "HttpRequestFactory";

    private HttpRequestFactory() {
    }

    public static HttpRequestFactory getInstance() {
        return LazyHolder.INSTANCE;
    }

    public HttpRequest getHttpRequest(InputStream in) throws IOException {
        BufferedReader br = getBufferedReader(in);

        String startLine = parseStartLine(br);
        String header = parseHeader(br);
        HttpStartLine httpStartLine = HttpStartLine.of(startLine);
        HttpRequestHeader httpRequestHeader = HttpRequestHeader.of(header);
        HttpRequestBody httpRequestBody = getHttpRequestBody(br, httpStartLine, httpRequestHeader);

        return new HttpRequest(httpStartLine, httpRequestHeader, httpRequestBody);
    }

    private BufferedReader getBufferedReader(InputStream in) {
        try {
            return new BufferedReader(new InputStreamReader(in, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("{} 클래스 에러 발생", TAG);
            throw new IllegalArgumentException("올바르지 않은 요청입니다.");
        }
    }

    private String parseStartLine(BufferedReader br) throws IOException {
        return br.readLine();
    }

    private String parseHeader(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String headerLine = br.readLine();
        while (!"".equals(headerLine)) {
            sb.append(headerLine);
            sb.append("\n");
            headerLine = br.readLine();
        }
        sb.append("\n");
        return sb.toString();
    }

    private HttpRequestBody getHttpRequestBody(BufferedReader br, HttpStartLine httpStartLine, HttpRequestHeader httpRequestHeader) {
        if (httpStartLine.checkMethod(HttpMethod.GET)) {
            return HttpRequestBody.empty();
        }

        String body = parseBody(br, httpRequestHeader.getContentLength());
        return HttpRequestBody.of(body);
    }

    private String parseBody(BufferedReader br, int contentLength) {
        String body = readBody(br, contentLength);
        return decodeBody(body);
    }

    private String readBody(BufferedReader br, int contentLength) {
        try {
            return IOUtils.readData(br, contentLength);
        } catch (IOException e) {
            log.error("{} 클래스 {} 메서드 오류", TAG, "parseBody");
            throw new IllegalArgumentException();
        }
    }

    private String decodeBody(String body) {
        try {
            String decode = URLDecoder.decode(body, "UTF-8");
            return decode;
        } catch (UnsupportedEncodingException e) {
            log.error("{} 클래스 {} 메서드 오류", TAG, "decodeBody");
            throw new IllegalArgumentException();
        }
    }

    private static class LazyHolder {
        private static final HttpRequestFactory INSTANCE = new HttpRequestFactory();
    }

}









