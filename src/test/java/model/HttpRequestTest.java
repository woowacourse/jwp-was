package model;

import static model.HttpRequest.*;
import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {
    private String request = "GET /index.html HTTP/1.1" + NEW_LINE
            + "Host: localhost:8080" + NEW_LINE
            + "Connection: keep-alive" + NEW_LINE
            + "Accept: */*" + NEW_LINE
            + EMPTY;

    private HttpRequest httpRequest = new HttpRequest(
            Arrays.stream(request.split(NEW_LINE))
                    .filter(value -> value != null && !value.isEmpty())
                    .collect(Collectors.toList()));

    @DisplayName("요청에 따른 HttpRequest를 생성한다.")
    @Test
    public void from() throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(request.getBytes())));

        HttpRequest actual = HttpRequest.from(br);

        assertThat(httpRequest).isEqualTo(actual);
    }

    @DisplayName("요청 주소를 얻어온다.")
    @Test
    public void getPath() {
        String expected = "/index.html";

        String actual = httpRequest.getPath();

        assertThat(expected).isEqualTo(actual);
    }
}