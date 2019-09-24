package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {
    @Test
    void pascalToKebobCaseTest() {
        assertThat(
                StringUtils.pascalToKebobCase("ContentType")
        ).isEqualTo("Content-Type");
    }

    @Test
    void screamingSnakeCaseToStartCaseTest() {
        assertThat(
                StringUtils.screamingSnakeCaseToStartCase("PREFIX_STATIC_RESOURCES")
        ).isEqualTo("Prefix Static Resources");
    }
}