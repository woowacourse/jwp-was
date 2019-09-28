package learningtest;

import org.apache.tika.Tika;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class TikaTest {
    @Test
    @DisplayName("파일 내용으로부터 MimeType 알아내기")
    void figureOutMimeTypeFromFileContent() throws IOException {
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html lang=\"kr\">\n" +
                "<head>\n" +
                "    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>SLiPP Java Web Programming</title>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n" +
                "    <link href=\"../css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "    <!--[if lt IE 9]>\n" +
                "    <script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>\n" +
                "    <![endif]-->\n" +
                "    <link href=\"../css/styles.css\" rel=\"stylesheet\">\n" +
                "</head>\n" +
                "<body>\n" +
                "Hello World\n" +
                "</body>";
        Tika tika = new Tika();
        String mimeType = tika.detect(new ByteArrayInputStream(htmlContent.getBytes()));

        assertThat(mimeType).isEqualTo("text/html");
    }

    @Test
    @DisplayName("파일 입력 스트림으로부터 MimeType 알아내기")
    void figureOutMimeTypeFromInputStream() throws IOException, URISyntaxException {
        Tika tika = new Tika();
        byte[] b = FileIoUtils.loadFileFromClasspath("./templates/user/profile.hbs");
        String mimeType = tika.detect(new ByteArrayInputStream(b));

        assertThat(mimeType).isEqualTo("text/html");
    }

    @Test
    @DisplayName("파일로부터 MimeType 알아내기")
    void figureOutMimeTypeFromFile() throws IOException, URISyntaxException {
        Tika tika = new Tika();
        File file = new File(FileIoUtils.getUriFromFilePath("./templates/user/profile.hbs").get());
        String mimeType = tika.detect(file);

        assertThat(mimeType).isEqualTo("text/html");
    }
}

