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
import webserver.Kind;
import webserver.dto.HttpRequest;

class HtmlFileHandlerTest {

    private static final Logger logger = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadHtmlFile() throws IOException, URISyntaxException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HttpRequest httpRequest = new HttpRequest("GET", "/index.html", "http/1.1", new HashMap<>(),
            Kind.WEBAPP_FILE);
        HtmlFileHandler.loadHtmlFile(os, httpRequest);

        byte[] body = FileIoUtils
            .loadFileFromClasspath(httpRequest.getUrlPath(), httpRequest.getKind().getDirectory());

        assertThat(os.toString()).contains("200 OK");
        assertThat(os.toByteArray()).contains(body);

        logger.debug("response : {}", os);
    }
}