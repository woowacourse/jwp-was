package model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParserTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void test() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        RequestParser request = new RequestParser(in);

        assertThat(request.getParameter().get("userId")).isEqualTo("javajigi");
    }
}