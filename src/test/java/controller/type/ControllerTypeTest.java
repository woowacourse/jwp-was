package controller.type;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestHeader;
import http.request.HttpRequestLine;
import http.request.HttpRequestUrl;

public class ControllerTypeTest {

    private HttpRequest httpRequestTemplate;
    private HttpRequest httpRequestStatic;
    private HttpRequest httpRequestGetUser;
    private HttpRequest httpRequestPostUser;

    @BeforeEach
    void setUp() {
        HttpRequestLine httpRequestLine1 = new HttpRequestLine("GET", new HttpRequestUrl("/index.html"), "HTTP/1.1");
        HttpRequestLine httpRequestLine2 = new HttpRequestLine("GET", new HttpRequestUrl("/css/style.css"), "HTTP/1.1");
        HttpRequestLine httpRequestLine3 = new HttpRequestLine("GET", new HttpRequestUrl("/user/create"), "HTTP/1.1");
        HttpRequestLine httpRequestLine4 = new HttpRequestLine("POST", new HttpRequestUrl("/user/create"), "HTTP/1.1");

        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(new HashMap<>());
        HttpRequestBody httpRequestBody = HttpRequestBody.emptyBody();

        httpRequestTemplate = new HttpRequest(httpRequestLine1, httpRequestHeader, httpRequestBody);
        httpRequestStatic = new HttpRequest(httpRequestLine2, httpRequestHeader, httpRequestBody);
        httpRequestGetUser = new HttpRequest(httpRequestLine3, httpRequestHeader, httpRequestBody);
        httpRequestPostUser = new HttpRequest(httpRequestLine4, httpRequestHeader, httpRequestBody);
    }

    @Test
    void findTemplate() {
        ControllerType actual = ControllerType.find(httpRequestTemplate);
        assertThat(actual).isEqualTo(ControllerType.TEMPLATE);
    }

    @Test
    void findStatic() {
        ControllerType actual = ControllerType.find(httpRequestStatic);
        assertThat(actual).isEqualTo(ControllerType.STATIC);
    }

    @Test
    void findGetUser() {
        ControllerType actual = ControllerType.find(httpRequestGetUser);
        assertThat(actual).isEqualTo(ControllerType.USER);
    }

    @Test
    void findPostUser() {
        ControllerType actual = ControllerType.find(httpRequestPostUser);
        assertThat(actual).isEqualTo(ControllerType.USER);
    }
}