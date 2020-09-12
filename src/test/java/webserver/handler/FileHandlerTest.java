package webserver.handler;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.FileIoUtilsTest;
import webserver.FileNameExtension;
import webserver.dto.HttpRequest;

class FileHandlerTest {

    private static final Logger logger = LoggerFactory.getLogger(FileIoUtilsTest.class);
    private static final String HTTP_METHOD = "GET";
    private static final String HTML_URL_PATH = "/index.html";
    private static final String CSS_URL_PATH = "/css/bootstrap.min.css";
    private static final String PROTOCOL = "http/1.1";

    @Test
    void loadFile_HTML() throws IOException, URISyntaxException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HttpRequest httpRequest = new HttpRequest(HTTP_METHOD, HTML_URL_PATH, PROTOCOL,
            new HashMap<>(),
            FileNameExtension.from(HTML_URL_PATH));
        FileHandler.loadFile(os, httpRequest);

        byte[] body = FileIoUtils
            .loadFileFromClasspath(httpRequest.getUrlPath(), httpRequest.getKind().getDirectory());

        assertThat(os.toString()).contains("200 OK");
        assertThat(os.toString()).contains("Content-Type: text/html");
        assertThat(os.toByteArray()).contains(body);

        logger.debug("response : {}", os);
    }

    @Test
    void loadFile_CSS() throws IOException, URISyntaxException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HttpRequest httpRequest = new HttpRequest(HTTP_METHOD, CSS_URL_PATH, PROTOCOL,
            new HashMap<>(),
            FileNameExtension.from(CSS_URL_PATH));
        FileHandler.loadFile(os, httpRequest);

        byte[] body = FileIoUtils
            .loadFileFromClasspath(httpRequest.getUrlPath(), httpRequest.getKind().getDirectory());

        assertThat(os.toString()).contains("200 OK");
        assertThat(os.toString()).contains("Content-Type: text/css");
        assertThat(os.toByteArray()).contains(body);

        logger.debug("response : {}", os);
    }
}