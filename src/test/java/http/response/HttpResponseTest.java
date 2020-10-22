package http.response;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.HttpStatus;
import http.HttpVersion;
import view.Model;
import view.View;

class HttpResponseTest {

    private OutputStream out = new ByteArrayOutputStream();
    private HttpResponse response;

    @BeforeEach
    void setUp() {
        response = HttpResponse.from(out);
    }

    @DisplayName("HTTP 1.1 버전 확인")
    @Test
    void check_Default_HttpVersion() {
        assertThat(response.getVersion()).isEqualTo(HttpVersion.HTTP1_1);
    }

    @DisplayName("응답에 리소스 없는 경우")
    @Test
    void has_No_Resource() {
        assertThat(response.hasResource()).isFalse();
    }

    @DisplayName("응답에 리소스 있는 경우")
    @Test
    void has_Resource() {
        response.ok(new View("/index.html"));
        assertThat(response.hasResource()).isTrue();
    }

    @DisplayName("응답에 모델 없는 경우")
    @Test
    void has_No_Model() {
        assertThat(response.hasModel()).isFalse();
    }

    @DisplayName("응답에 모델 있는 경우")
    @Test
    void has_Model() {
        response.ok(new View("/index.html"), new Model());
        assertThat(response.hasResource()).isTrue();
    }

    @DisplayName("OK 응답 상태코드 200")
    @Test
    void response_OK_StatusCode_200() {
        response.ok(new View("/index.html"));
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("리다이렉트 상태코드 302")
    @Test
    void redirect_StatusCode_302() {
        response.redirect("/index.html");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
    }
}
