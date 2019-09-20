package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpRequest.HttpRequestBody;
import webserver.httpRequest.HttpRequestHeader;
import webserver.httpRequest.HttpStartLine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class HttpRequestFactory {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestFactory.class);
    private static final String TAG = "HttpRequestFactory";

    public HttpRequest getHttpRequest(InputStream in) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("{} 클래스 에러 발생", TAG);
        }

        HttpStartLine httpStartLine = HttpStartLine.of(br);
        HttpRequestHeader httpRequestHeader = HttpRequestHeader.of(br);

        HttpRequestBody httpRequestBody = null;
        if (httpStartLine.isGet()) {
            httpRequestBody = HttpRequestBody.Empty();
        }

        if (httpStartLine.isPost()) {
            httpRequestBody = HttpRequestBody.of(br, httpRequestHeader.getContentLength());
        }

        return new HttpRequest(httpStartLine, httpRequestHeader, httpRequestBody);
    }


}
