package http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RequestTest {

    private Request request;

    @BeforeEach
    void setUp() {
        RequestLine requestLine = new RequestLine("GET", "/user/create", "HTTP/1.1");
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.add("Accept", "text/html");
        RequestBody requestBody = new RequestBody("userId=javajigi&password=password&name=자바지기&email=javajigi@slipp.net");

        request = new Request(requestLine, requestHeader, requestBody);
    }

    @Test
    void getUri() {
        assertThat(request.getUri()).isEqualTo("/user/create");
    }

    @Test
    void getHeaderByName() {
        assertThat(request.getHeaderByName("Accept")).isEqualTo("text/html");
    }

    @Test
    void getBodyByName() {
        assertThat(request.getBodyByName("userId")).isEqualTo("javajigi");
        assertThat(request.getBodyByName("password")).isEqualTo("password");
        assertThat(request.getBodyByName("name")).isEqualTo("자바지기");
        assertThat(request.getBodyByName("email")).isEqualTo("javajigi@slipp.net");
    }
}