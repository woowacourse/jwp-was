package utils;

import exception.InvalidFilePathException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class RequestUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    // TODO: 2020/09/09 html이 아닌 css, js 파일에 대한 테스트 추가
    @Test
    @DisplayName("RequestHeader에서 대상값을 전닳할 시 이를 바탕으로 올바른 파일명을 추출한다")
    void getFilePathInRequestHeaderTest() {
        String request = "/index.html";

        Assertions.assertThat(RequestUtils.getFilePath(request)).isEqualTo("./templates/index.html");
    }

    @Test
    @DisplayName("RequestHeader에서 잘못된 대상값을 전달할 시 예외를 발생시킨다")
    void getFilePathInRequestHeaderExceptionTest() {
        String invaildRequest = "Es Muss Sein!";

        Assertions.assertThatThrownBy(() -> RequestUtils.getFilePath(invaildRequest))
                .isInstanceOf(InvalidFilePathException.class)
                .hasMessage("잘못된 파일 경로입니다");
    }
}
