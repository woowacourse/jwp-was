package web;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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

        assertThat(requestBody.getBody()).isEqualTo(
            "userId=sonypark&password=sony123&name=sony&email=sonypark0204@gmail.com");

    }
}
