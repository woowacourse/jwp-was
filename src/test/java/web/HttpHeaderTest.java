package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeaderTest {

    @DisplayName("RequestHeader headers 확인")
    @Test
    void requestHeaderTest2() {
        List<String> lines = new ArrayList<>();
        lines.add("Host: localhost:8080");
        lines.add("Connection: keep-alive");

        HttpHeader httpHeader = new HttpHeader(lines);

        Map<String, String> headers = httpHeader.getHeaders();

        assertThat(headers.get("Host")).isEqualTo("localhost:8080");
        assertThat(headers.get("Connection")).isEqualTo("keep-alive");
    }

    @DisplayName("헤더를 추가한다.")
    @Test
    void putTest() {
        HttpHeader httpHeader = new HttpHeader();

        httpHeader.put("Location", "/index.html");

        assertThat(httpHeader.get("Location")).isEqualTo("/index.html");
    }

    @DisplayName("헤더를 response의 outputStream에 전부 쓴다.")
    @Test
    void writeTest() throws IOException {
        HttpHeader httpHeader = new HttpHeader();
        httpHeader.put("Location", "/index.html");
        httpHeader.put("Content-type", "text/html");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);

        httpHeader.write(dataOutputStream);
        dataOutputStream.flush();

        byte[] bytes = byteArrayOutputStream.toByteArray();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
        List<String> headers = bufferedReader.lines().collect(Collectors.toList());

        assertThat(headers).contains("Location: /index.html");
        assertThat(headers).contains("Content-type: text/html");
    }
}