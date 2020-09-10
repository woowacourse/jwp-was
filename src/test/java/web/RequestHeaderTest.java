package web;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static web.RequestHeader.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHeaderTest {
    private String request = "GET /index.html HTTP/1.1" + NEW_LINE
            + "Host: localhost:8080" + NEW_LINE
            + "Connection: keep-alive" + NEW_LINE
            + "Accept: */*" + NEW_LINE
            + EMPTY;

    private RequestHeader requestHeader = new RequestHeader(
            Arrays.stream(request.split(NEW_LINE))
                    .filter(value -> value != null && !value.isEmpty())
                    .collect(Collectors.toList()));

    @DisplayName("요청의 RequestHeader를 생성한다.")
    @Test
    public void from() throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(request.getBytes())));

        RequestHeader actual = new HttpRequest(br).getRequestHeader();

        assertEquals(requestHeader, actual);
    }

    @DisplayName("요청의 RequestUri를 생성한다.")
    @Test
    public void getPath() {
        RequestUri expected = new RequestUri("/index.html");

        RequestUri actual = requestHeader.getRequestUri();

        assertEquals(expected, actual);
    }
}