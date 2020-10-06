package http;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class StaticResourceTypeTest {
    private static Stream<Arguments> provideMatches() {
        return Stream.of(
                Arguments.of(Uri.from("index.html"), true),
                Arguments.of(Uri.from("hello.text"), false)
        );
    }

    @DisplayName("findByUri: uri를 입력받아 일치하는 패턴의 리소스를 찾는다.")
    @Test
    void findByUri() {
        // given
        Uri uri = Uri.from("index.html");

        // when
        StaticResourceType staticResourceType = StaticResourceType.findByUri(uri);

        // then
        assertThat(staticResourceType).isEqualTo(StaticResourceType.HTML);
    }

    @DisplayName("findByUri: uri와 일치하는 리소스 확장자가 없으면 예외가 발생한다.")
    @Test
    void findByUri_NotMatchUri_ExceptionThrown() {
        // given
        Uri uri = Uri.from("application.yml");

        // when
        // then
        assertThatThrownBy(() -> StaticResourceType.findByUri(uri))
                .isInstanceOf(StaticResourceTypeNotFoundException.class)
                .hasMessageContaining("일치하는 정적 자원을 찾을 수 없습니다.");
    }

    @DisplayName("matches: 확장자가 일치하는 정적 자원이 있는지 확인한다.")
    @MethodSource("provideMatches")
    @ParameterizedTest
    void matches(final Uri uri, final boolean expect) {
        assertThat(StaticResourceType.anyMatch(uri)).isEqualTo(expect);
    }
}
