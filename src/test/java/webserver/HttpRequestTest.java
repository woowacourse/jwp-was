package webserver;

import org.junit.jupiter.api.Test;
import utils.HttpRequestUtils;
import webserver.request.HttpRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";
    final String INDEX_URL = "/index.html";

    @Test
    void getPath_indexUrl_true() throws IOException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_index_test.txt"));
        HttpRequest request = new HttpRequest(inputStream);
        assertThat(request.getFilePath()).isEqualTo(HttpRequestUtils.ROOT_FILE_PATH + INDEX_URL);
    }

    @Test
    void getPath_root_true() throws IOException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_root_test.txt"));
        HttpRequest request = new HttpRequest(inputStream);
        assertThat(request.getFilePath()).isEqualTo(HttpRequestUtils.ROOT_FILE_PATH + INDEX_URL);
    }

    @Test
    void getParameter_userInfo_true() throws IOException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_form_param_test.txt"));
        HttpRequest request = new HttpRequest(inputStream);
        assertThat(request.getParam("userId")).isEqualTo("id");
        assertThat(request.getParam("password")).isEqualTo("password");
        assertThat(request.getParam("name")).isEqualTo("gyu");
    }

}