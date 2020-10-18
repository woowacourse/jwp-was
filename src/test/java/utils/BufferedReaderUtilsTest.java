package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BufferedReaderUtilsTest {
    @DisplayName("request에서 header부분만을 map으로 읽어들인다")
    @Test
    void readHeader() throws IOException {
        String data = "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: 46\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Cookie: _ga=GA1.1.1321239274.1597885260; logined=true\n"
                + "Accept: */*\n"
                + "\n"
                + "userId=javajigi&password=password&name=JaeSung\n";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        Map<String, String> actual = BufferedReaderUtils.readHeader(br);

        Map<String, String> expected = new HashMap<>();
        expected.put("Host", "localhost:8080");
        expected.put("Connection", "keep-alive");
        expected.put("Content-Length", "46");
        expected.put("Content-Type", "application/x-www-form-urlencoded");
        expected.put("Cookie", "_ga=GA1.1.1321239274.1597885260; logined=true");
        expected.put("Accept", "*/*");

        assertThat(actual).isEqualTo(expected);
    }
}
