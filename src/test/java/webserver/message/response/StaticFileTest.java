package webserver.message.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.StaticFile;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class StaticFileTest {

    @Test
    @DisplayName("정상적인 path를 넘겨받는 경우")
    void create1() throws IOException, URISyntaxException {
        final String path = "./static/css/styles.css";
        final StaticFile file = new StaticFile(path);

        assertThat(file.getPath()).isEqualTo(path);
        assertThat(file.getName()).isEqualTo("styles.css");
        assertThat(file.getExtension()).isEqualTo("css");
    }

    @Test
    @DisplayName("파일 이름 안에 .이 포함된 경우")
    void create2() throws IOException, URISyntaxException {
        final String path = "./static/css/bootstrap.min.css";
        final StaticFile file = new StaticFile(path);

        assertThat(file.getPath()).isEqualTo(path);
        assertThat(file.getName()).isEqualTo("bootstrap.min.css");
        assertThat(file.getExtension()).isEqualTo("css");
    }
}