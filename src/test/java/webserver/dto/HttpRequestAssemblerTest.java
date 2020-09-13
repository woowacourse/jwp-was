package webserver.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static util.Constants.PROTOCOL;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpMethod;

class HttpRequestAssemblerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestAssemblerTest.class);
    private static final String REQUEST_LINE_FORMAT =
        HttpMethod.GET.name() + " %s " + PROTOCOL + System.lineSeparator();

    @DisplayName("Parameter가 없는 URL 요청시")
    @ParameterizedTest
    @ValueSource(strings = {"/index.html", "/index.html?"})
    void assemble_hasNotParameters(String url) throws IOException {
        String request = String.format(REQUEST_LINE_FORMAT, url) + System.lineSeparator();
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

    @DisplayName("Parameter가 있는 URL 요청시")
    @ParameterizedTest
    @ValueSource(strings = {"/index.html?1234=abcd", "/index.html?1234=abcd&12345=abcd",
        "/index.html?1234=&12345=abcd"})
    void assemble_hasParameters(String url) throws IOException {
        String request = String.format(REQUEST_LINE_FORMAT, url) + System.lineSeparator();
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

    @DisplayName("Parameter에 Key만 있는 URL 요청시 - Parameter Skip")
    @ParameterizedTest
    @ValueSource(strings = {"/index.html?1234", "/index.html?1234=abcd&12345"})
    void assemble_hasParametersOnlyKey(String url) throws IOException {
        String request = String.format(REQUEST_LINE_FORMAT, url) + System.lineSeparator();
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
}