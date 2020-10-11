package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class HeadersTest {

    @ParameterizedTest
    @DisplayName("Headers of")
    @ValueSource(strings = {
        "src/test/resources/input/get_template_file_request.txt",
        "src/test/resources/input/get_static_file_request.txt",
        "src/test/resources/input/get_api_request.txt",
        "src/test/resources/input/post_api_request.txt"
    })
    void create(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        bufferedReader.readLine();  // read unnecessary line (request line)

        Headers headers = Headers.of(bufferedReader);

        assertThat(headers).isInstanceOf(Headers.class);
    }

    @ParameterizedTest
    @DisplayName("Message Body 포함 여부 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:false",
        "src/test/resources/input/get_static_file_request.txt:false",
        "src/test/resources/input/get_api_request.txt:false",
        "src/test/resources/input/post_api_request.txt:true"
    }, delimiter = ':')
    void hasContent(String filePath, boolean expected) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        bufferedReader.readLine();  // read unnecessary line (request line)

        Headers headers = Headers.of(bufferedReader);
        assertThat(headers.hasContent()).isEqualTo(expected);
    }
}
