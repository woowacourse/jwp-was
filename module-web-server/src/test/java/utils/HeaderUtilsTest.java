package utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import model.general.Header;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HeaderUtilsTest {

    @Test
    @DisplayName("Header Map 생성 테스트")
    void generateHeaders() throws IOException {
        String filePath = "src/test/resources/input/get_api_request.txt";
        InputStream inputStream = new FileInputStream(filePath);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        bufferedReader.readLine();

        Map<Header, String> headers = HeaderUtils.generateHeaders(bufferedReader);

        assertAll(
            () -> assertThat(headers).isInstanceOf(Map.class),
            () -> assertThat(headers).containsKey(Header.HOST),
            () -> assertThat(headers).containsKey(Header.CONNECTION),
            () -> assertThat(headers).containsKey(Header.COOKIE),
            () -> assertThat(headers).containsKey(Header.ACCEPT)
        );
    }
}
