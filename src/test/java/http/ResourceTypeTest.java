package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ResourceTypeTest {

    @DisplayName("경로가 정적 파일을 요청 하는 요청인지 확인할 수 있다.")
    @CsvSource({"/index.html, true", "./css/styles.css, true", "./js/scripts.js, true", "/user, false"})
    @ParameterizedTest
    void isStaticFile(String path, boolean expectedResult) {
        boolean isStaticFile = ResourceType.isStaticFile(path);
        assertThat(isStaticFile).isEqualTo(expectedResult);
    }

    @DisplayName("요청 경로를 통해 요청하는 정적 파일 타입을 알아낼 수 있다.")
    @CsvSource({"/index.html, ./templates, text/html;charset=UTF-8", "./css/styles.css, ./static, text/css;charset=UTF-8", "./js/scripts.js, ./static, application/javascript;charset=UTF-8"})
    @ParameterizedTest
    void from(String path, String expectedBaseDirectory, String expectedContentType) {
        ResourceType resourceType = ResourceType.from(path);
        assertAll(
                () -> assertThat(resourceType.getBaseDirectory()).isEqualTo(expectedBaseDirectory),
                () -> assertThat(resourceType.getContentType()).isEqualTo(expectedContentType)
        );
    }

    @DisplayName("예외 테스트: 요청하는 경로에 해당하는 정적 파일 타입이 없으면 예외가 발생한다.")
    @Test
    void fromExceptionTest() {
        String notStaticFile = "/user";
        assertThatThrownBy(() -> ResourceType.from(notStaticFile))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("요청에 해당하는 정적 자원이 타입이 없습니다.");
    }
}