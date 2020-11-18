package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;

import webserver.http.request.HttpRequest;

public class HttpRequestFixture {
    public static HttpRequest httpRequestOfGetMethod() throws IOException {
        ClassPathResource resource = new ClassPathResource("GetRequest.txt");
        return HttpRequest.of(resource.getInputStream());
    }

    public static HttpRequest httpRequestWithEmptyParameter() throws IOException {
        ClassPathResource resource = new ClassPathResource("GetRequestWithEmptyParameter.txt");
        return HttpRequest.of(resource.getInputStream());
    }

    public static HttpRequest httpRequestOfPostMethod() throws IOException {
        ClassPathResource resource = new ClassPathResource("PostRequest.txt");
        return HttpRequest.of(resource.getInputStream());
    }

    public static HttpRequest httpRequestOfTemplatesResource() throws IOException {
        ClassPathResource resource = new ClassPathResource("TemplatesResourceRequest.txt");
        return HttpRequest.of(resource.getInputStream());
    }

    public static HttpRequest httpRequestOfPutMethod() throws IOException {
        ClassPathResource resource = new ClassPathResource("PutRequest.txt");
        return HttpRequest.of(resource.getInputStream());
    }

    public static BufferedReader bufferedReaderOfRequestLine() throws IOException {
        ClassPathResource resource = new ClassPathResource("RequestLine.txt");
        return new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
    }

    public static BufferedReader bufferedReaderOfEmptyRequestLine() throws IOException {
        ClassPathResource resource = new ClassPathResource("EmptyRequestLine.txt");
        return new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
    }

    public static HttpRequest httpRequestOfLogin() throws IOException {
        ClassPathResource resource = new ClassPathResource("usercreaterequest/PostRequest.txt");
        return HttpRequest.of(resource.getInputStream());
    }
}
