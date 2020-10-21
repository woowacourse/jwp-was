package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import http.request.HttpRequest;
import http.request.RequestBody;
import http.request.RequestHeader;
import http.request.RequestLine;
import utils.TestFileResourceLoader;

public abstract class AbstractHttpRequestGenerator {

    protected static HttpRequest createHttpGetRequest(String fileName) throws IOException {
        InputStream inputStream = TestFileResourceLoader.fetchTestFile(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        RequestLine requestLine = new RequestLine(bufferedReader);
        RequestHeader requestHeader = new RequestHeader(bufferedReader);
        return HttpRequest.of(requestLine, requestHeader, null, cookies, parameters);
    }

    protected static HttpRequest createHttpPostRequest(String fileName) throws IOException {
        InputStream inputStream = TestFileResourceLoader.fetchTestFile(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        RequestLine requestLine = new RequestLine(bufferedReader);
        RequestHeader requestHeader = new RequestHeader(bufferedReader);
        RequestBody requestBody = new RequestBody(bufferedReader, requestHeader.getContentLength());

        return HttpRequest.of(requestLine, requestHeader, requestBody, cookies, parameters);
    }
}

