package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpBodyTest {

    @DisplayName("올바른 입력에 대해 HttpBody를 생성한다.")
    @Test
    void createHttpBody() throws IOException {
        String input = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Reader inputString = new StringReader(input);
        BufferedReader reader = new BufferedReader(inputString);

        int contentLength = input.length();

        HttpBody httpBody = new HttpBody(reader, contentLength);

        assertAll(
            () -> assertThat("javajigi").isEqualTo(httpBody.getValue("userId")),
            () -> assertThat("password").isEqualTo(httpBody.getValue("password")),
            () -> assertThat("%EB%B0%95%EC%9E%AC%EC%84%B1").isEqualTo(httpBody.getValue("name")),
            () -> assertThat("javajigi%40slipp.net").isEqualTo(httpBody.getValue("email"))
        );
    }
}