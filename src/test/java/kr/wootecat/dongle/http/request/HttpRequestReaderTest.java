package kr.wootecat.dongle.http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.wootecat.dongle.http.HttpMethod;

class HttpRequestReaderTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/%s";

    @DisplayName("HTTP GET Request 요청을 HttpRequest 객체로 파싱한다.")
    @Test
    void parseGetRequest() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(String.format(TEST_DIRECTORY, "GET_REQUEST.txt")));
        HttpRequest httpRequest = HttpRequestReader.parse(reader);

        assertAll(
                () -> assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(httpRequest.getPath()).isEqualTo("/index.html"),
                () -> assertThat(httpRequest.getProtocol()).isEqualTo("HTTP/1.1"),

                () -> assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(httpRequest.getHeader("Accept-Encoding")).isEqualTo("gzip, deflate, br"),
                () -> assertThat(httpRequest.getHeader("Accept-Language")).isEqualTo(
                        "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7"),

                () -> assertThat(httpRequest.getCookie("JSESSIONID")).isEqualTo("1fb5b72c-fed0-42a3-9e8c-6197b446af76")
        );
    }

    @DisplayName("Query parameter가 존재하는 HTTP GET Request 요청을 HttpRequest 객체로 파싱한다.")
    @Test
    void parseGetRequestWithQueryParam() throws Exception {
        BufferedReader reader = new BufferedReader(
                new FileReader(String.format(TEST_DIRECTORY, "GET_REQUEST_WITH_QUERY_PARAM.txt")));
        HttpRequest httpRequest = HttpRequestReader.parse(reader);

        assertAll(
                () -> assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(httpRequest.getPath()).isEqualTo("/user/create"),
                () -> assertThat(httpRequest.getProtocol()).isEqualTo("HTTP/1.1"),

                () -> assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(httpRequest.getHeader("Content-Length")).isEqualTo("93"),
                () -> assertThat(httpRequest.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded"),
                () -> assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*"),

                () -> assertThat(httpRequest.getCookie("JSESSIONID")).isEqualTo("1fb5b72c-fed0-42a3-9e8c-6197b446af76"),

                () -> assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi"),
                () -> assertThat(httpRequest.getParameter("password")).isEqualTo("password"),
                () -> assertThat(httpRequest.getParameter("name")).isEqualTo("박재성"),
                () -> assertThat(httpRequest.getParameter("email")).isEqualTo("javajigi@slipp.net")
        );
    }

    @DisplayName("HTTP POST Request 요청을 HttpRequest 객체로 파싱한다.")
    @Test
    void parsePostRequest() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(String.format(TEST_DIRECTORY, "POST_REQUSET.txt")));
        HttpRequest httpRequest = HttpRequestReader.parse(reader);

        assertAll(
                () -> assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.POST),
                () -> assertThat(httpRequest.getPath()).isEqualTo("/user/create"),
                () -> assertThat(httpRequest.getProtocol()).isEqualTo("HTTP/1.1"),

                () -> assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(httpRequest.getHeader("Content-Length")).isEqualTo("93"),
                () -> assertThat(httpRequest.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded"),
                () -> assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*"),

                () -> assertThat(httpRequest.getCookie("JSESSIONID")).isEqualTo("1fb5b72c-fed0-42a3-9e8c-6197b446af76"),

                () -> assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi"),
                () -> assertThat(httpRequest.getParameter("password")).isEqualTo("password"),
                () -> assertThat(httpRequest.getParameter("name")).isEqualTo("박재성"),
                () -> assertThat(httpRequest.getParameter("email")).isEqualTo("javajigi@slipp.net")
        );
    }
}