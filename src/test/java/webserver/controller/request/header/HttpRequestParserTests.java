package webserver.controller.request.header;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.controller.request.HttpRequestParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestParserTests {
    private static final String TEST_PATH = "./src/test/resources/HttpRequestParserTestDatas/";
    private BufferedReader bufferedReader;

    private void setTestData(String filePath) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File(TEST_PATH + filePath));
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Test
    void parseRequestLine() throws IOException {
        setTestData("responseLineData.txt");
        String[] requestLine = HttpRequestParser.parseRequestLine(bufferedReader);
        assertThat(requestLine[0]).isEqualTo("POST");
        assertThat(requestLine[1]).isEqualTo("/user/create");
        assertThat(requestLine[2]).isEqualTo("HTTP/1.1");
    }

    @Test
    void parseHeaderFields() throws IOException {
        setTestData("responseHeaderFieldsData.txt");
        HashMap<String, String> headerFields = HttpRequestParser.parseHeaderFields(bufferedReader);
        assertThat(headerFields.get("Host")).isEqualTo("localhost:8080");
        assertThat(headerFields.get("Connection")).isEqualTo("keep-alive");
        assertThat(headerFields.get("Content-Length")).isEqualTo("47");
        assertThat(headerFields.get("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(headerFields.get("Accept")).isEqualTo("*/*");
    }

    @Test
    void parseRequestBody() throws IOException {
        setTestData("responseBodyData.txt");
        HashMap<String,String> bodyFields = HttpRequestParser.parseBody(bufferedReader,47);
        assertThat(bodyFields.get("userId")).isEqualTo("kangmin46");
        assertThat(bodyFields.get("password")).isEqualTo("password");
        assertThat(bodyFields.get("name")).isEqualTo("kangmin");
    }
}
