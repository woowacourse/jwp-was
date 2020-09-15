package http.request;

import java.io.BufferedReader;
import java.io.IOException;

import exception.RequestEntityCreateFailException;
import http.HttpBody;
import http.HttpHeader;

public class RequestEntity {
    private static final String START_LINE_SEPERATOR = " ";
    private static final int METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int VERSION_INDEX = 2;
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";

    private HttpMethod httpMethod;
    private HttpUrl httpUrl;
    private String httpVersion;
    private HttpHeader httpHeader;
    private HttpBody httpBody;

    private RequestEntity(HttpMethod httpMethod, HttpUrl httpUrl, String httpVersion, HttpHeader httpHeader) {
        this.httpMethod = httpMethod;
        this.httpUrl = httpUrl;
        this.httpVersion = httpVersion;
        this.httpHeader = httpHeader;
        this.httpBody = null;
    }

    private RequestEntity(
        HttpMethod httpMethod, HttpUrl httpUrl, String httpVersion,
        HttpHeader httpHeader, HttpBody httpBody
    ) {
        this.httpMethod = httpMethod;
        this.httpUrl = httpUrl;
        this.httpVersion = httpVersion;
        this.httpHeader = httpHeader;
        this.httpBody = httpBody;
    }

    public static RequestEntity from(BufferedReader bufferedReader) {
        try {
            String[] splittedStartLine = bufferedReader.readLine().split(START_LINE_SEPERATOR);
            HttpMethod httpMethod = HttpMethod.valueOf(splittedStartLine[METHOD_INDEX]);
            HttpUrl httpUrl = HttpUrl.from(splittedStartLine[URL_INDEX]);
            String httpVersion = splittedStartLine[VERSION_INDEX];

            HttpHeader httpHeader = HttpHeader.from(bufferedReader);
            String contentLength = httpHeader.findOrEmpty(CONTENT_LENGTH);
            if (!contentLength.isEmpty()) {
                ContentType contentType = ContentType.findOrDefault(httpHeader.findOrEmpty(CONTENT_TYPE));
                HttpBody httpBody = HttpBody.from(bufferedReader, contentType,
                    Integer.parseInt(contentLength));
                return new RequestEntity(httpMethod, httpUrl, httpVersion, httpHeader, httpBody);
            }
            return new RequestEntity(httpMethod, httpUrl, httpVersion, httpHeader);
        } catch (IOException e) {
            throw new RequestEntityCreateFailException();
        }
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpUrl getHttpUrl() {
        return httpUrl;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public HttpBody getHttpBody() {
        return httpBody;
    }
}
