package utils;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BufferedReaderUtilsTest {
    @Test
    void readHeader() throws IOException {
        String data = "POST /user/create HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: 46\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Cookie: _ga=GA1.1.1321239274.1597885260; logined=true\n"
                + "Accept: */*\n"
                + "\n"
                + "userId=javajigi&password=password&name=JaeSung\n";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        List<String> actual = BufferedReaderUtils.readHeader(br);

        List<String> expected = Arrays.asList(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 46",
                "Content-Type: application/x-www-form-urlencoded",
                "Cookie: _ga=GA1.1.1321239274.1597885260; logined=true",
                "Accept: */*"
        );

        assertThat(actual).isEqualTo(expected);
    }
}
