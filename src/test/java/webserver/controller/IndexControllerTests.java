package webserver.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.controller.response.HttpResponse;
import webserver.controller.response.HttpStatus;

import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static webserver.ModelAndView.NON_STATIC_FILE_PATH;

public class IndexControllerTests extends BasicTests {
    private IndexController indexController;

    @BeforeEach
    void setUp() {
        indexController = new IndexController();
    }

    @Test
    void doGet() throws IOException {
        makeRequest("Http_GET.txt");
        HttpResponse httpResponse = indexController.doGet(httpRequest);
        assertThat(httpResponse.getVersion()).isEqualTo("HTTP/1.1");
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody()).isEqualTo(FileIoUtils.loadFileFromClasspath("." + NON_STATIC_FILE_PATH + "/index.html").get());
    }
}
