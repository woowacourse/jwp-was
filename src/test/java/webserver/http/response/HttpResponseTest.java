package webserver.http.response;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.assertj.core.api.MapAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpResponseTest {
    @DisplayName("Http 버전에 맞는 Response를 생성한다.")
    @Test
    void ofVersion() {
        assertThat(HttpResponse.ofVersion("HTTP/1.1")).isNotNull();
    }

    @DisplayName("문자열을 입력했을 때 해당하는 문자열에 맞게 응답을 만든다.")
    @Test
    void writeBody() throws Exception {
        HttpResponse httpResponse = HttpResponse.ofVersion("HTTP/1.1");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(out);

        httpResponse.writeBody("hello");

        httpResponse.write(dataOutputStream);
        dataOutputStream.flush();

        MapAssert<Object, Object> mapAssert = assertThat(httpResponse)
            .extracting("responseHeaders")
            .extracting("headers")
            .asInstanceOf(MAP);
        assertAll(
            () -> assertThat(httpResponse)
                .extracting("responseStatus")
                .extracting("httpStatus")
                .extracting("statusCode").isEqualTo(200),
            () -> mapAssert
                .extractingByKey("Content-Type")
                .isEqualTo("text/html;charset=utf-8"),
            () -> mapAssert
                .extractingByKey("Content-Length")
                .isEqualTo("5"),
            () -> assertThat(out.toString()).contains("hello")
        );
    }

    @DisplayName("지정한 주소에 맞게 리다이텍트하는 응답을 만든다.")
    @Test
    void sendRedirect() {
        HttpResponse httpResponse = HttpResponse.ofVersion("HTTP/1.1");

        httpResponse.sendRedirect("/index.html");

        MapAssert<Object, Object> mapAssert = assertThat(httpResponse)
            .extracting("responseHeaders")
            .extracting("headers")
            .asInstanceOf(MAP);
        assertAll(
            () -> assertThat(httpResponse)
                .extracting("responseStatus")
                .extracting("httpStatus")
                .extracting("statusCode").isEqualTo(302),
            () -> mapAssert
                .extractingByKey("Location")
                .isEqualTo("/index.html")
        );
    }

    @DisplayName("에러를 표시하는 응답을 만든다.")
    @Test
    void sendError() throws Exception {
        HttpResponse httpResponse = HttpResponse.ofVersion("HTTP/1.1");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(out);

        httpResponse.sendError(HttpStatus.NOT_FOUND, "not found");

        httpResponse.write(dataOutputStream);
        dataOutputStream.flush();

        MapAssert<Object, Object> mapAssert = assertThat(httpResponse)
            .extracting("responseHeaders")
            .extracting("headers")
            .asInstanceOf(MAP);
        assertAll(
            () -> assertThat(httpResponse)
                .extracting("responseStatus")
                .extracting("httpStatus")
                .extracting("statusCode").isEqualTo(404),
            () -> mapAssert
                .extractingByKey("Content-Type")
                .isEqualTo("text/html;charset=utf-8"),
            () -> mapAssert
                .extractingByKey("Content-Length")
                .isEqualTo("9"),
            () -> assertThat(out.toString()).contains("not found")
        );
    }

    @DisplayName("잘못된 예외 코드가 오면 예외처리한다.")
    @Test
    void sendErrorWithException() {
        HttpResponse httpResponse = HttpResponse.ofVersion("HTTP/1.1");

        assertThatThrownBy(() -> httpResponse.sendError(HttpStatus.OK, "not found"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("에러 코드만");
    }
}