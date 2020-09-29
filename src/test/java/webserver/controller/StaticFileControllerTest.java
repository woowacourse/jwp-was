package webserver.controller;

import exception.CustomExceptionStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.response.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

class StaticFileControllerTest {

    @DisplayName("잘못된 경로로 요청할 때 예외 처리를 해준다.")
    @Test
    void exceptionTest() {
        final String NOT_EXIST_PATH = "FILE_DOES_NOT_EXIST";
        HttpResponse httpResponse = StaticFileController.staticProcess(NOT_EXIST_PATH);

        byte[] expected = CustomExceptionStatus.FILE_NOT_EXIST.getMessage().getBytes();
        assertThat(httpResponse.getBody()).isEqualTo(expected);
    }
}