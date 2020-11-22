package kr.wootecat.dongle.http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import kr.wootecat.dongle.http.HttpMethod;
import kr.wootecat.dongle.http.exception.IllegalRequestDataFormatException;

class HttpRequestReaderTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/%s";

    @DisplayName("HTTP GET Request 요청을 HttpRequest 객체로 파싱한다.")
    @Test
    void parseGetRequest() throws Exception {
        BufferedReader reader = new BufferedReader(
                new FileReader(String.format(TEST_DIRECTORY, "VALID_GET_REQUEST.txt")));
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
                new FileReader(String.format(TEST_DIRECTORY, "VALID_GET_REQUEST_WITH_QUERY_PARAM.txt")));
        HttpRequest httpRequest = HttpRequestReader.parse(reader);

        assertAll(
                () -> assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(httpRequest.getPath()).isEqualTo("/user/create"),
                () -> assertThat(httpRequest.getProtocol()).isEqualTo("HTTP/1.1"),

                () -> assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*"),

                () -> assertThat(httpRequest.getCookie("JSESSIONID")).isEqualTo("1fb5b72c-fed0-42a3-9e8c-6197b446af76"),

                () -> assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi"),
                () -> assertThat(httpRequest.getParameter("password")).isEqualTo("password"),
                () -> assertThat(httpRequest.getParameter("name")).isEqualTo("박재성"),
                () -> assertThat(httpRequest.getParameter("email")).isEqualTo("javajigi@slipp.net")
        );
    }

    @DisplayName("파일명에 여러 온점(.)이 포함된 정적 자원의 HTTP GET Request 요청을 HttpRequest 객체로 파싱한다.")
    @Test
    void parseStaticResourceGetRequestWithMultipleDotFile() throws Exception {
        BufferedReader reader = new BufferedReader(
                new FileReader(
                        String.format(TEST_DIRECTORY, "VALID_STATIC_RESOURCE_GET_REQUEST_WITH_MULTIPLE_DOT.txt")));
        HttpRequest httpRequest = HttpRequestReader.parse(reader);

        assertAll(
                () -> assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(httpRequest.getPath()).isEqualTo("/css/bootstrap.min.css"),
                () -> assertThat(httpRequest.getProtocol()).isEqualTo("HTTP/1.1"),

                () -> assertThat(httpRequest.getHeader("Accept")).isEqualTo("text/css,*/*;q=0.1"),
                () -> assertThat(httpRequest.getHeader("Accept-Encoding")).isEqualTo("gzip, deflate, br"),
                () -> assertThat(httpRequest.getHeader("Accept-Language")).isEqualTo(
                        "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7"),
                () -> assertThat(httpRequest.getHeader("Cache-Control")).isEqualTo("no-cache"),
                () -> assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(httpRequest.getHeader("Pragma")).isEqualTo("no-cache"),
                () -> assertThat(httpRequest.getHeader("Referer")).isEqualTo("http://localhost:8080/index.html"),
                () -> assertThat(httpRequest.getHeader("Sec-Fetch-Dest")).isEqualTo("style"),
                () -> assertThat(httpRequest.getHeader("Sec-Fetch-Mode")).isEqualTo("no-cors"),
                () -> assertThat(httpRequest.getHeader("Sec-Fetch-Site")).isEqualTo("same-origin"),
                () -> assertThat(httpRequest.getHeader("User-Agent")).isEqualTo(
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36"),

                () -> assertThat(httpRequest.getCookie("JSESSIONID")).isEqualTo("1fb5b72c-fed0-42a3-9e8c-6197b446af76"),
                () -> assertThat(httpRequest.getCookie("logined")).isEqualTo("true")
        );
    }

    @DisplayName("HTTP POST Request 요청을 HttpRequest 객체로 파싱한다.")
    @Test
    void parsePostRequest() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(String.format(TEST_DIRECTORY,
                "VALID_POST_REQUEST.txt")));
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

    @DisplayName("유효하지 않는 HTTP 요청은 request 객체 파싱중 IllegalRequestDataFormatException을 던진다.")
    @ParameterizedTest
    @CsvSource(value = {"INVALID_GET_REQUEST.txt", "INVALID_GET_REQUEST2.txt", "INVALID_GET_REQUEST3.txt",
            "INVALID_GET_REQUEST4.txt", "INVALID_GET_REQUEST_WITH_QUERY_PARAM.txt",
            "INVALID_POST_REQUEST.txt"})
    void parseIllegalFormatRequest(String illegalHttpRequestFile) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(String.format(TEST_DIRECTORY,
                illegalHttpRequestFile)));

        assertThatThrownBy(() -> HttpRequestReader.parse(reader))
                .isInstanceOf(IllegalRequestDataFormatException.class);
    }
}