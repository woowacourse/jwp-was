package http.factory;

import http.HttpMethod;
import http.HttpRequest;
import http.RequestHeader;
import http.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpRequestFactoryTest {

    @DisplayName("Request를 만드는 메서드 테스트 - get방식")
    @Test
    void toRequestWhenGet() throws IOException {
        String requestHeader = "GET /index.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n"
                + "";

        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Host", "localhost:8080");
        headerParams.put("Connection", "keep-alive");
        headerParams.put("Accept", "*/*");

        RequestHeader expectHeader = new RequestHeader(headerParams);
        RequestLine expectUri = new RequestLine(HttpMethod.GET, "/index.html");

        BufferedReader reader = new BufferedReader(new StringReader(requestHeader));
        HttpRequest actualHttpRequest = HttpRequestFactory.createRequest(reader);

        assertAll(
                () -> assertThat(actualHttpRequest.getRequestHeader()).isEqualToComparingFieldByField(expectHeader),
                () -> assertThat(actualHttpRequest.getRequestLine()).isEqualTo(expectUri)
        );
    }

    @DisplayName("Request를 만드는 메서드 테스트 - post방식")
    @Test
    void toRequestWhenPost() throws IOException {
        String body = "userId=id&password=1234&name=name&email=email@email";
        String requestHeader = "POST /user/create HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Content-Length: " + body.length() + "\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "\n"
                + body;

        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");
        headers.put("Content-Length", "51");
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> data = new HashMap<>();
        data.put("userId", "id");
        data.put("password", "1234");
        data.put("name", "name");
        data.put("email", "email@email");

        RequestHeader expectHeader = new RequestHeader(headers);
        RequestLine expectUri = new RequestLine(HttpMethod.POST, "/user/create");

        BufferedReader reader = new BufferedReader(new StringReader(requestHeader));
        HttpRequest actualHttpRequest = HttpRequestFactory.createRequest(reader);

        assertAll(
                () -> assertThat(actualHttpRequest.getRequestHeader()).isEqualToComparingFieldByField(expectHeader),
                () -> assertThat(actualHttpRequest.getParams()).isEqualTo(data),
                () -> assertThat(actualHttpRequest.getRequestLine().getUrl()).isEqualTo(expectUri.getUrl()),
                () -> assertThat(actualHttpRequest.getRequestLine().getMethod()).isEqualTo(expectUri.getMethod())
        );
    }

}
