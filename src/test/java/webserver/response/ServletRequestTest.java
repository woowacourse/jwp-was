package webserver.response;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.request.ServletRequest;

class ServletRequestTest {

    @DisplayName("요청을 정상적으로 생성하는 지 확인")
    @Test
    void create() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/request.txt"));
        InputStreamReader ir = new InputStreamReader(fileInputStream);
        BufferedReader br = new BufferedReader(ir);
        ServletRequest servletRequest = new ServletRequest(br);
        Map<String, String> body = servletRequest.getBody();

        assertAll(
            () -> assertThat(servletRequest.getHeader("Host")).isEqualTo("localhost:8080"),
            () -> assertThat(servletRequest.getHeader("Connection")).isEqualTo("keep-alive"),
            () -> assertThat(servletRequest.getHeader("Content-Length")).isEqualTo("93"),
            () -> assertThat(servletRequest.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded"),
            () -> assertThat(servletRequest.getHeader("Accept")).isEqualTo("*/*"),
            () -> assertThat(body).containsKeys("userId", "password", "name", "email")
        );
    }
}
