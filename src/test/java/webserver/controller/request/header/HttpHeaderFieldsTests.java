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
import static webserver.controller.request.header.HttpBasicTests.TEST_DIRECTORY;

public class HttpHeaderFieldsTests {
    public static final int CONTENT_LENGTH = 46;
    private HttpHeaderFields httpHeaderFields;
    private BufferedReader bufferedReader;

    @BeforeEach
    void setUp() throws IOException {
        InputStream inputStream = new FileInputStream(new File(TEST_DIRECTORY + "Http_POST.txt"));
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        httpHeaderFields = new HttpHeaderFields(bufferedReader);
    }

    @Test
    void getContentsLength() throws IOException {
        assertThat(httpHeaderFields.readData(bufferedReader).length()).isEqualTo(CONTENT_LENGTH);
    }
}
