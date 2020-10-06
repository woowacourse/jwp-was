package utils;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @DisplayName("readBody: 요청 body를 읽는다.")
    @Test
    public void readBody() throws Exception {
        // given
        String expect = "abcd123";
        StringReader stringReader = new StringReader(expect);
        BufferedReader bufferedReader = new BufferedReader(stringReader);

        // when
        String actual = IOUtils.readBody(bufferedReader, expect.length());

        // then
        assertThat(actual).isEqualTo(expect);
    }

    @DisplayName("readHeader: 요청 header를 읽는다.")
    @Test
    void readHeader() throws IOException {
        // given
        String expect = "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Cache-Control: max-age=0\n"
                + "Upgrade-Insecure-Requests: 1\n"
                + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36 Edg/85.0.564.44\n"
                + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n"
                + "Sec-Fetch-Site: none\n"
                + "Sec-Fetch-Mode: navigate\n"
                + "Sec-Fetch-User: ?1\n"
                + "Sec-Fetch-Dest: document\n"
                + "Accept-Encoding: gzip, deflate, br\n"
                + "Accept-Language: ko,en;q=0.9,en-US;q=0.8\n";
        StringReader stringReader = new StringReader(expect);
        BufferedReader bufferedReader = new BufferedReader(stringReader);

        // when
        List<String> actual = IOUtils.readHeader(bufferedReader);

        // then
        assertThat(actual).hasSize(12);
    }
}
