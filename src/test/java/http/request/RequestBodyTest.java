package http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestBodyTest {

    @DisplayName("RequestBody 조회")
    @Test
    void check_RequestBody_Content() throws IOException {
        String request = "POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 71\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "\n"
            + "userId=sonypark&password=sony123&name=sony&email=sonypark0204@gmail.com";

        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(request.getBytes())));

        RequestLine requestLine = new RequestLine(bufferedReader);
        RequestHeader requestHeader = new RequestHeader(bufferedReader);
        RequestBody requestBody = new RequestBody(bufferedReader, requestHeader.getContentLength());
        Map<String, String> parsedBody = requestBody.parseBody();

        assertAll(
            () -> assertThat(parsedBody.get("userId")).isEqualTo("sonypark"),
            () -> assertThat(parsedBody.get("password")).isEqualTo("sony123"),
            () -> assertThat(parsedBody.get("name")).isEqualTo("sony"),
            () -> assertThat(parsedBody.get("email")).isEqualTo("sonypark0204@gmail.com")
        );

    }
}
