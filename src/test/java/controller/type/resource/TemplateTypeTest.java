package controller.type.resource;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TemplateTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"html,HTML", "ico,ICO", "css,NONE"})
    void find(final String fileType, TemplateType expected) {
        final TemplateType actual = TemplateType.find(fileType);
        assertThat(actual).isEqualTo(expected);
    }
}