package utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.jknack.handlebars.Template;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HandlebarUtilsTest {

    private static final String LIST_REQUEST = "/user/list";
    private static final String INVALID_LIST_REQUEST = "/user/invalid-list";

    @Test
    @DisplayName("템플릿 로딩")
    void loadHandlebar() throws IOException {
        assertThat(HandlebarUtils.loadHandlebar(LIST_REQUEST)).isInstanceOf(Template.class);
    }

    @Test
    @DisplayName("템플릿 로딩 - 존재하지 않는 템플릿")
    void loadHandlebar_NotExistTemplate_ThrowException() throws IOException {
        assertThatThrownBy(() -> HandlebarUtils.loadHandlebar(INVALID_LIST_REQUEST))
            .isInstanceOf(IOException.class);
    }
}
