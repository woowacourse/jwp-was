package webserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilesTest.class);

    @DisplayName("확장자에 따라 MIME 타입 추출 확인")
    @ParameterizedTest
    @ValueSource(strings = {"/index.txt", "/index", "/index.html", "/index.js", "/index.css",
        "/css/bootstrap.min.css", "/glyphicons-halflings-regular.woff", "/haha.woff"})
    void probeContentType(String urlPath) throws IOException {
        String mimeType = Files.probeContentType(Paths.get(urlPath));
        LOGGER.debug("urlPath : {}, MimeType : {}", urlPath, mimeType);
    }
}
