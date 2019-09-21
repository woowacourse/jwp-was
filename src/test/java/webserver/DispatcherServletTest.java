package webserver;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import http.RequestParameter;
import org.junit.jupiter.api.Test;
import webserver.exception.NotFoundResourceException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DispatcherServletTest {
    @Test
    void static_파일_요청() {
        HttpRequest request = new HttpRequest.HttpRequestBuilder()
                .method(HttpMethod.GET)
                .uri("/css/styles.css")
                .build();
        HttpResponse response = new HttpResponse();
        assertDoesNotThrow(() -> DispatcherServlet.doDispatch(request, response));
    }

    @Test
    void 존재하지않는_static_파일_요청() {
        HttpRequest request = new HttpRequest.HttpRequestBuilder()
                .method(HttpMethod.GET)
                .uri("/css/styles.cs")
                .build();
        HttpResponse response = new HttpResponse();
        assertThrows(NotFoundResourceException.class, () -> DispatcherServlet.doDispatch(request, response));
    }

    @Test
    void 일반_URL_요청() {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", "easy");
        userInfo.put("name", "easy");
        userInfo.put("password", "easyeasy");
        userInfo.put("email", "easy@gmail.com");
        HttpRequest request = new HttpRequest.HttpRequestBuilder()
                .method(HttpMethod.GET)
                .uri("/user/create")
                .requestParameter(new RequestParameter(userInfo))
                .build();
        HttpResponse response = new HttpResponse();
        assertDoesNotThrow(() -> DispatcherServlet.doDispatch(request, response));
    }
}