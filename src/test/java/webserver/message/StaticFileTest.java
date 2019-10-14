package webserver.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.file.File;
import webserver.file.StaticFile;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class StaticFileTest {
    private static final String STATIC_PATH = "./static";

    @Test
    @DisplayName("정상적인 path를 넘겨받는 경우")
    void create1() throws IOException, URISyntaxException {
        //final String path = "./static/css/styles.css";
        final String path = "/css/styles.css";
        final File file = new StaticFile(path);

        assertThat(file.getPath()).isEqualTo(STATIC_PATH + path);
        assertThat(file.getName()).isEqualTo("styles.css");
        assertThat(file.getExtension()).isEqualTo("css");
    }

    @Test
    @DisplayName("파일 이름 안에 .이 포함된 경우")
    void create2() throws IOException, URISyntaxException {
        //final String path = "./static/css/bootstrap.min.css";
        final String path = "/css/bootstrap.min.css";
        final File file = new StaticFile(path);

        assertThat(file.getPath()).isEqualTo(STATIC_PATH + path);
        assertThat(file.getName()).isEqualTo("bootstrap.min.css");
        assertThat(file.getExtension()).isEqualTo("css");
    }
}