package http.request;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @Test
    @DisplayName("간단한 요청")
    void simpleRequest() throws IOException {
        // Arrange
        String request = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";
        InputStream in = IOUtils.toInputStream(request, "UTF-8");

        // Act
        HttpRequest httpRequest = HttpRequest.of(in);

        // Assert
        assertStartLine(httpRequest, HttpMethod.GET, "/index.html");
        assertHeaders(httpRequest, Arrays.asList(
                Arrays.asList("Host", "localhost:8080"),
                Arrays.asList("Connection", "keep-alive"),
                Arrays.asList("Accept", "*/*")
        ));
    }

    @Test
    @DisplayName("파라미터 값이 uri 에 존재할 경우")
    void requestHasParameterInUri() throws IOException {
        // Arrange
        String requestWithParameters = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";
        InputStream in = IOUtils.toInputStream(requestWithParameters, "UTF-8");

        // Act
        HttpRequest httpRequest = HttpRequest.of(in);

        // Assert
        assertStartLine(httpRequest, HttpMethod.GET, "/user/create");
        assertHeaders(httpRequest, Arrays.asList(
                Arrays.asList("Host", "localhost:8080"),
                Arrays.asList("Connection", "keep-alive"),
                Arrays.asList("Accept", "*/*")
        ));
        assertParameters(httpRequest, Arrays.asList(
                Arrays.asList("userId", "javajigi"),
                Arrays.asList("password", "password"),
                Arrays.asList("name", "박재성"),
               Arrays.asList("email", "javajigi@slipp.net")
        ));
    }

    @Test
    @DisplayName("파라미터 값이 body 에 존제할 경우")
    void requestHasParameterInBody() throws IOException {
        // Arrange
        String requestWithBody = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        InputStream in = IOUtils.toInputStream(requestWithBody, "UTF-8");

        // Act
        HttpRequest httpRequest = HttpRequest.of(in);

        // Assert
        assertStartLine(httpRequest, HttpMethod.POST, "/user/create");
        assertHeaders(httpRequest, Arrays.asList(
                Arrays.asList("Host", "localhost:8080"),
                Arrays.asList("Connection", "keep-alive"),
                Arrays.asList("Content-Length", "93"),
                Arrays.asList("Content-Type", "application/x-www-form-urlencoded"),
                Arrays.asList("Accept", "*/*")
        ));
        assertParameters(httpRequest, Arrays.asList(
                Arrays.asList("userId", "javajigi"),
                Arrays.asList("password", "password"),
                Arrays.asList("name", "박재성"),
                Arrays.asList("email", "javajigi@slipp.net")
        ));
    }

    private void assertBody(HttpRequest request, String expectedBody) {
        assertThat(request.hasBody()).isTrue();

        assertThat(request.getBody()).isEqualTo(expectedBody);
    }

    @Test
    @DisplayName("파라미터 값이 uri 와 body 에 동시에 존재할 경우")
    void parametersInUriAndBody() throws IOException {
        // Arrange
        String queryStringInUri = "userId=javajigi&password=password";
        String requestWithParametersInUriAndBody = "POST /user/create?" + queryStringInUri + " HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        InputStream in = IOUtils.toInputStream(requestWithParametersInUriAndBody, "UTF-8");

        // Act
        HttpRequest httpRequest = HttpRequest.of(in);

        // Arrange
        assertStartLine(httpRequest, HttpMethod.POST, "/user/create");
        assertHeaders(httpRequest, Arrays.asList(
                Arrays.asList("Host", "localhost:8080"),
                Arrays.asList("Connection", "keep-alive"),
                Arrays.asList("Content-Length", "59"),
                Arrays.asList("Content-Type", "application/x-www-form-urlencoded"),
                Arrays.asList("Accept", "*/*")
        ));
        assertParameters(httpRequest, Arrays.asList(
                Arrays.asList("userId", "javajigi"),
                Arrays.asList("password", "password"),
                Arrays.asList("name", "박재성"),
                Arrays.asList("email", "javajigi@slipp.net")
        ));
    }

    private void assertStartLine(HttpRequest request, HttpMethod expectedMethod, String expectedPath) {
        assertThat(request.getHttpMethod()).isEqualTo(expectedMethod);
        assertThat(request.getPath()).isEqualTo(expectedPath);
    }

    private void assertHeaders(HttpRequest request, List<List<String>> expectedHaders) {
        for (List<String> expectedHeader : expectedHaders) {
            String key = expectedHeader.get(0);
            String value = expectedHeader.get(1);

            assertThat(request.getHeader(key)).isEqualTo(value);
        }
    }

    private void assertParameters(HttpRequest request, List<List<String>> expectedParameters) {
        assertThat(request.hasParameters()).isTrue();

        for (List<String> expectedParameter : expectedParameters) {
            String key = expectedParameter.get(0);
            String value = expectedParameter.get(1);

            assertThat(request.getParameter(key)).isEqualTo(value);
        }
    }
}
