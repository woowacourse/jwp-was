package webserver.http;

import exception.FileNotReadableException;
import exception.InvalidUriException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utils.FileIoUtils;
import webserver.http.request.HttpUri;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpUriTest {

    @DisplayName("/로 시작하는 URI로 HttpUri 객체 생성")
    @ParameterizedTest
    @ValueSource(strings = {"/index.html", "/user/create", "/create?id=abc"})
    void httpUriTest(String uri) {
        assertThat(HttpUri.from(uri)).isInstanceOf(HttpUri.class);
    }

    @DisplayName("/로 시작하지 않는 URI로 HttpUri 객체를 만들면 InvalidUriException 발생")
    @ParameterizedTest
    @ValueSource(strings = {"index.html", "?join/form.html", "."})
    void httpUriExceptionTest(String uri) {
        assertThatThrownBy(() -> HttpUri.from(uri))
                .isInstanceOf(InvalidUriException.class)
                .hasMessage("규격에 맞지 않는 URI입니다! -> " + uri);
    }

    @DisplayName("templates에 존재하는 resource를 읽기")
    @Test
    void readFileTest() throws Exception {
        byte[] indexBody = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        HttpUri httpUri = HttpUri.from("/index.html");

        assertThat(httpUri.readFile()).isEqualTo(indexBody);
    }

    @DisplayName("templates에 존재하지 않는 resource이면 FileNotReadableException 발생")
    @Test
    void readFileExceptionTest() {
        HttpUri httpUri = HttpUri.from("/styles.css");

        assertThatThrownBy(httpUri::readFile)
                .isInstanceOf(FileNotReadableException.class)
                .hasMessageContaining("읽을 수 없는 파일입니다! -> ");
    }
}