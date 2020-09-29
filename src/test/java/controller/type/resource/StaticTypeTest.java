package controller.type.resource;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class StaticTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"css,CSS", "js,JS", "woff,WOFF", "woff2,WOFF2", "html,NONE"})
    void find(final String fileType, StaticType expected) {
        final StaticType actual = StaticType.find(fileType);
        assertThat(actual).isEqualTo(expected);
    }
}