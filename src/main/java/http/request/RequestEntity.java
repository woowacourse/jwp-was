package http.request;

import java.io.BufferedReader;
import java.io.IOException;

import exception.RequestEntityCreateFailException;

public class RequestEntity {
    private HttpMethod httpMethod;
    private HttpUrl httpUrl;
    private String httpVersion;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    private RequestEntity(HttpMethod httpMethod, HttpUrl httpUrl, String httpVersion, RequestHeader requestHeader) {
        this.httpMethod = httpMethod;
        this.httpUrl = httpUrl;
        this.httpVersion = httpVersion;
        this.requestHeader = requestHeader;
        this.requestBody = null;
    }

    private RequestEntity(
        HttpMethod httpMethod, HttpUrl httpUrl, String httpVersion,
        RequestHeader requestHeader, RequestBody requestBody
    ) {
        this.httpMethod = httpMethod;
        this.httpUrl = httpUrl;
        this.httpVersion = httpVersion;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static RequestEntity from(BufferedReader bufferedReader) {
        try {
            String[] splittedStartLine = bufferedReader.readLine().split(" ");
            HttpMethod httpMethod = HttpMethod.valueOf(splittedStartLine[0]);
            HttpUrl httpUrl = HttpUrl.from(splittedStartLine[1]);
            String httpVersion = splittedStartLine[2];

            RequestHeader requestHeader = RequestHeader.from(bufferedReader);
            String contentLength = requestHeader.findOrEmpty("Content-Length");
            if (!contentLength.isEmpty()) {
                ContentType contentType = ContentType.findOrDefault(requestHeader.findOrEmpty("Content-Type"));
                RequestBody requestBody = RequestBody.from(bufferedReader, contentType, Integer.parseInt(contentLength));
                return new RequestEntity(httpMethod, httpUrl, httpVersion, requestHeader, requestBody);
            }
            return new RequestEntity(httpMethod, httpUrl, httpVersion, requestHeader);
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

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
