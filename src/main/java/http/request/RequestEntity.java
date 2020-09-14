package http.request;

import java.io.BufferedReader;
import java.io.IOException;

import exception.RequestEntityCreateFailException;

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
            String[] splittedStartLine = bufferedReader.readLine().split(START_LINE_SEPERATOR);
            HttpMethod httpMethod = HttpMethod.valueOf(splittedStartLine[METHOD_INDEX]);
            HttpUrl httpUrl = HttpUrl.from(splittedStartLine[URL_INDEX]);
            String httpVersion = splittedStartLine[VERSION_INDEX];

            RequestHeader requestHeader = RequestHeader.from(bufferedReader);
            String contentLength = requestHeader.findOrEmpty(CONTENT_LENGTH);
            if (!contentLength.isEmpty()) {
                ContentType contentType = ContentType.findOrDefault(requestHeader.findOrEmpty(CONTENT_TYPE));
                RequestBody requestBody = RequestBody.from(bufferedReader, contentType,
                    Integer.parseInt(contentLength));
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
