package utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HandlebarUtilsTest {

    private static final String LIST_REQUEST = "/user/list";
    private static final String INVALID_LIST_REQUEST = "/user/invalid-list";

    @Test
    @DisplayName("템플릿 적용")
    void applyTemplate() throws IOException {
        assertThat(HandlebarUtils.applyTemplate(LIST_REQUEST, null))
            .isInstanceOf(String.class);
    }

    @Test
    @DisplayName("템플릿 적용 - 존재하지 않는 템플릿")
    void loadHandlebar_NotExistTemplate_ThrowException() {
        assertThatThrownBy(() -> HandlebarUtils.applyTemplate(INVALID_LIST_REQUEST, null))
            .isInstanceOf(IOException.class);
    }
}
