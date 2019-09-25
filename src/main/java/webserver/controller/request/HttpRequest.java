package webserver.controller.request;

import jdk.jfr.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.request.body.HttpRequestBody;
import webserver.controller.request.header.HttpMethod;
import webserver.controller.request.header.HttpRequestLine;
import webserver.controller.request.header.HttpHeaderFields;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final HttpRequestLine httpRequestLine;
    private final HttpHeaderFields httpHeaderFields;
    private final Map<String, String> httpRequestBodyFields;

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String[] requestline = HttpRequestParser.parseRequestLine(bufferedReader);
        this.httpRequestLine = new HttpRequestLine(requestline);

        this.httpHeaderFields = new HttpHeaderFields(HttpRequestParser.parseHeaderFields(bufferedReader));
        this.httpRequestBodyFields = setRequestBody(bufferedReader);
    }

    private Map<String, String> setRequestBody(BufferedReader bufferedReader) throws IOException {
        HttpMethod httpMethod =  httpRequestLine.getHttpMethod();
        if(httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT) {
            return HttpRequestParser.parseBody(bufferedReader, httpHeaderFields.getContentLength());
        }
        return new HashMap<>();
    }

    public MimeType getMimeType() {
        return httpRequestLine.getMimeType();
    }

    public String getPath() {
        return httpRequestLine.getUrl();
    }

    public String getVersion() {
        return httpRequestLine.getVersion();
    }

    public HttpMethod getHttpMethod() {
        return httpRequestLine.getHttpMethod();
    }

    public Map<String, String> getBodyFields() {
        return this.httpRequestBodyFields;
    }
}
