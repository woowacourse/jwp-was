package file;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testhelper.Common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class FileContainerTest {
    private static final Logger logger = LoggerFactory.getLogger(FileContainerTest.class);

    @Test
    @DisplayName("정적파일에 대한 페이지를 true를 반환한다")
    public void processStaticFileWhenSuccess() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpRequest httpRequest = HttpRequestFactory.create(Common.getBufferedReaderOfTextFile("HTTP_GET_CSS.txt"));
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        FileContainer fileContainer = new FileContainer();
        assertThat(fileContainer.process(httpRequest, httpResponse)).isEqualTo(true);

        logger.info("\n" + byteArrayOutputStream);
    }

    @Test
    @DisplayName("정적파일이 아닐 때 false를 반환한다")
    public void processStaticFileWhenFail() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpRequest httpRequest = HttpRequestFactory.create(
                Common.getBufferedReaderOfTextFile("HTTP_GET_QUERY_STRING.txt"));
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        FileContainer fileContainer = new FileContainer();
        assertThat(fileContainer.process(httpRequest, httpResponse)).isEqualTo(false);

        logger.info("\n" + byteArrayOutputStream);
    }
}