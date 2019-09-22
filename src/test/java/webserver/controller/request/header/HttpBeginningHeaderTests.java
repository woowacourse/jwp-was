package webserver.controller.request.header;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static webserver.controller.request.header.HttpBasicTests.TEST_DIRECTORY;

public class HttpBeginningHeaderTests {
    private HttpBeginningHeader httpBeginningHeader;

    @BeforeEach
    void setUp() throws IOException {
        InputStream inputStream = new FileInputStream(new File(TEST_DIRECTORY + "Http_GET.txt"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        httpBeginningHeader = new HttpBeginningHeader(bufferedReader);
    }

    @Test
    void url() {
        assertThat(httpBeginningHeader.getUrl()).isEqualTo("/index.html");
    }

    @Test
    void method() {
        assertThat(httpBeginningHeader.getHttpMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void version() {
        assertThat(httpBeginningHeader.getVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void invalidContentType() {
        assertDoesNotThrow(() -> httpBeginningHeader.getContentType());
    }
}
