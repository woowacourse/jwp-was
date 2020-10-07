package jwp.was.webserver.dto;

import static java.lang.System.lineSeparator;
import static jwp.was.util.Constants.HTTP_VERSION;
import static jwp.was.util.Constants.URL_PATH_INDEX_HTML;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import jwp.was.webserver.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HttpRequestAssemblerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestAssemblerTest.class);
    private static final String REQUEST_GET_LINE_FORMAT =
        HttpMethod.GET.name() + " %s " + HTTP_VERSION + lineSeparator();
    private static final String REQUEST_POST_LINE_FORMAT =
        HttpMethod.POST.name() + " %s " + HTTP_VERSION + lineSeparator();
    private static final String HEADER_CONTENT_LENGTH_FORMAT = "Content-Length: %d";
    private static final String TEST_RESOURCES = "./src/test/resources/";

    @DisplayName("QueryString이 없는 URL 요청시")
    @ParameterizedTest
    @ValueSource(strings = {"/index.html", "/index.html?"})
    void assemble_hasNotQueryString(String url) throws IOException {
        String request = String.format(REQUEST_GET_LINE_FORMAT, url) + lineSeparator();
        HttpRequest httpRequest;
        try (InputStream in = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
            BufferedReader br = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {
            httpRequest = HttpRequestAssembler.assemble(br);
        }

        LOGGER.debug("urlPath : {}", httpRequest.getUrlPath());
        LOGGER.debug("parameters : {}", httpRequest.getParameters());

        assertThat(httpRequest.getUrlPath()).isEqualTo(url.split("\\?")[0]);
        assertThat(httpRequest.getParameters()).isEmpty();
    }

    @DisplayName("QueryString이 있는 URL 요청시")
    @ParameterizedTest
    @ValueSource(strings = {"/index.html?1234=abcd", "/index.html?1234=abcd&12345=abcd",
        "/index.html?1234=&12345=abcd"})
    void assemble_hasQueryString(String url) throws IOException {
        String request = String.format(REQUEST_GET_LINE_FORMAT, url) + lineSeparator();
        HttpRequest httpRequest;
        try (InputStream in = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
            BufferedReader br = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {
            httpRequest = HttpRequestAssembler.assemble(br);
        }

        LOGGER.debug("urlPath : {}", httpRequest.getUrlPath());
        LOGGER.debug("parameters : {}", httpRequest.getParameters());

        String[] urlParameters = url.split("\\?");
        assertThat(httpRequest.getUrlPath()).isEqualTo(urlParameters[0]);

        int parameterCount = urlParameters[1].split("&").length;
        assertThat(httpRequest.getParameters()).hasSize(parameterCount);
    }

    @DisplayName("QueryString에 Key만 있는 URL 요청시 - Parameter Skip")
    @ParameterizedTest
    @ValueSource(strings = {"/index.html?1234", "/index.html?1234=abcd&12345"})
    void assemble_hasQueryStringOnlyKey(String url) throws IOException {
        String request = String.format(REQUEST_GET_LINE_FORMAT, url) + lineSeparator();
        HttpRequest httpRequest;
        try (InputStream in = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
            BufferedReader br = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {
            httpRequest = HttpRequestAssembler.assemble(br);
        }

        LOGGER.debug("urlPath : {}", httpRequest.getUrlPath());
        LOGGER.debug("parameters : {}", httpRequest.getParameters());

        String[] urlParameters = url.split("\\?");
        assertThat(httpRequest.getUrlPath()).isEqualTo(urlParameters[0]);

        int parameterCount = urlParameters[1].split("&").length - 1;
        assertThat(httpRequest.getParameters()).hasSize(parameterCount);
    }

    @DisplayName("body가 있는 POST 요청시")
    @ParameterizedTest
    @CsvSource(value = {"empty, 0", "bodyKey=bodyValue, 1", "bodyKey=bodyValue&key=value, 2"})
    void assemble_hasBody(String body, int parameterCount) throws IOException {
        int bodyLength = body.getBytes(StandardCharsets.UTF_8).length;
        String contentLengthHeader = String.format(HEADER_CONTENT_LENGTH_FORMAT, bodyLength);
        String request =
            String.format(REQUEST_POST_LINE_FORMAT, URL_PATH_INDEX_HTML) + contentLengthHeader
                + lineSeparator() + lineSeparator() + body;
        HttpRequest httpRequest;
        try (InputStream in = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
            BufferedReader br = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {
            httpRequest = HttpRequestAssembler.assemble(br);
        }

        LOGGER.debug("urlPath : {}", httpRequest.getUrlPath());
        LOGGER.debug("parameters : {}", httpRequest.getParameters());

        assertThat(httpRequest.getParameters()).hasSize(parameterCount);
    }

    @DisplayName("Header가 있는 요청시")
    @ParameterizedTest
    @ValueSource(strings = {"Http_GET.txt", "Http_POST.txt"})
    void assemble_hasHeaders(String fileName) throws IOException {
        try (InputStream in = new FileInputStream(new File(TEST_RESOURCES + fileName));
            BufferedReader br = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {
            HttpRequest httpRequest = HttpRequestAssembler.assemble(br);

            Map<String, String> headers = httpRequest.getHeaders();
            assertThat(headers).isNotNull();
            assertThat(headers).isNotEmpty();

            assertThat("keep-alive").isEqualTo(httpRequest.getHeader("Connection"));
            assertThat("jamie9504").isEqualTo(httpRequest.getParameter("userId"));
        }
    }
}
