package webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import webserver.http.request.HttpRequest;

public class HttpRequestFixture {
    public static HttpRequest httpRequestOfGetMethod() throws IOException {
        String filePath = "/Users/moon/Desktop/Github/jwp-was/build/resources/test/GetRequest.txt";
        return HttpRequest.of(inputStream(filePath));
    }

    public static HttpRequest httpRequestWithEmptyParameter() throws IOException {
        String filePath = "/Users/moon/Desktop/Github/jwp-was/build/resources/test/GetRequestWithEmptyParameter.txt";
        return HttpRequest.of(inputStream(filePath));
    }

    public static HttpRequest httpRequestOfPostMethod() throws IOException {
        String filePath = "/Users/moon/Desktop/Github/jwp-was/build/resources/test/PostRequest.txt";
        return HttpRequest.of(inputStream(filePath));
    }

    public static HttpRequest httpRequestOfTemplatesResource() throws IOException {
        String filePath = "/Users/moon/Desktop/Github/jwp-was/build/resources/test/TemplatesResourceRequest.txt";
        return HttpRequest.of(inputStream(filePath));
    }

    public static HttpRequest httpRequestOfPutMethod() throws IOException {
        String filePath = "/Users/moon/Desktop/Github/jwp-was/build/resources/test/PutRequest.txt";
        return HttpRequest.of(inputStream(filePath));
    }

    public static BufferedReader bufferedReaderOfRequestLine() throws FileNotFoundException {
        String filePath = "/Users/moon/Desktop/Github/jwp-was/build/resources/test/RequestLine.txt";
        return new BufferedReader(new InputStreamReader(inputStream(filePath), StandardCharsets.UTF_8));
    }

    public static BufferedReader bufferedReaderOfEmptyRequestLine() throws FileNotFoundException {
        String filePath = "/Users/moon/Desktop/Github/jwp-was/build/resources/test/EmptyRequestLine.txt";
        return new BufferedReader(new InputStreamReader(inputStream(filePath), StandardCharsets.UTF_8));
    }

    private static InputStream inputStream(String filePath) throws FileNotFoundException {
        return new FileInputStream(new File(filePath));
    }
}
