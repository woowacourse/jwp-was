package http;

import http.request.RequestInformation;
import http.request.RequestMethod;
import http.request.RequestUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestInformationTest {

    @Test
    @DisplayName("GET시 제대로 method 생성하는지 테스트")
    void createGetTest() {
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "GET /index.html HTTP/1.1");
        RequestInformation requestInformation = new RequestInformation(information);
        assertThat(requestInformation.extractMethod()).isEqualTo(RequestMethod.GET);
    }

    @Test
    @DisplayName("POST시 제대로 method 생성하는지 테스트")
    void createPostTest() {
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "POST /user/create HTTP/1.1");
        RequestInformation requestInformation = new RequestInformation(information);
        assertThat(requestInformation.extractMethod()).isEqualTo(RequestMethod.POST);
    }

    @Test
    @DisplayName("PUT시 제대로 method 생성하는지 테스트")
    void createPutTest() {
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "PUT /user/1 HTTP/1.1");
        RequestInformation requestInformation = new RequestInformation(information);
        assertThat(requestInformation.extractMethod()).isEqualTo(RequestMethod.PUT);
    }

    @Test
    @DisplayName("Delete시 제대로 method 생성하는지 테스트")
    void createDeleteTest() {
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "DELETE /user/1 HTTP/1.1");
        RequestInformation requestInformation = new RequestInformation(information);
        assertThat(requestInformation.extractMethod()).isEqualTo(RequestMethod.DELETE);
    }

    @Test
    @DisplayName("제대로 URL생성하는지 테스트")
    void getUrlPath() {
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "GET /user/create HTTP/1.1");
        RequestInformation requestInformation = new RequestInformation(information);
        assertThat(requestInformation.extractUrl()).isEqualTo(RequestUrl.from("/user/create"));
    }

    @Test
    @DisplayName("제대로 URL생성하는지 테스트2")
    void postUrlPath() {
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "GET /user/create/friend HTTP/1.1");
        RequestInformation requestInformation = new RequestInformation(information);
        assertThat(requestInformation.extractUrl()).isEqualTo(RequestUrl.from("/user/create/friend"));
    }

//    @Test
//    @DisplayName("Query-Parameters가 url가 body에 있을 때 제대로 가져오는지 테스트")
//    void postWithQueryParamsInUrlAndBody() {
//        Map<String, String> information = new HashMap<>();
//        information.put("Request-Line:", "POST /user/create/?id=1 HTTP/1.1");
//        information.put("Host:", "localhost:8080");
//        information.put("Connection:", "keep-alive");
//        information.put("Content-Length:", "59");
//        information.put("Content-Type:", "application/x-www-form-urlencoded");
//        information.put("Accept:", "*/*");
//        information.put("Query-Parameters:", "userId=javajigi&password=password&name=jm&email=jm@gmail.com");
//        RequestInformation requestInformation = new RequestInformation(information);
//        assertThat(requestInformation.extractQueryParameters()).isEqualTo("userId=javajigi&password=password&name=jm&email=jm@gmail.com&id=1");
//    }
}
