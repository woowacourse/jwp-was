package utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import model.general.Cookie;
import model.general.Headers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CookieUtilsTest {

    @Test
    @DisplayName("Cookie List 생성 테스트")
    void generateCookies() throws IOException {
        String filePath = "src/test/resources/input/get_api_request.txt";
        InputStream inputStream = new FileInputStream(filePath);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        bufferedReader.readLine();
        Headers headers = new Headers(HeaderUtils.generateHeaders(bufferedReader));

        List<Cookie> cookies = CookieUtils.generateCookies(headers);

        assertAll(
            () -> assertThat(cookies).isInstanceOf(List.class),
            () -> assertThat(cookies).isNotEmpty(),
            () -> assertThat(cookies).hasSize(2)
        );
    }
}
