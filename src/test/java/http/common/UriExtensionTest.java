package http.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UriExtensionTest {

    @Test
    @DisplayName("HTML,ICO 파일 외 다른 파일의 file path Prefix가 /templates로 나오는지 확인")
    void get_path_prefix_of_html_file() {
        assertThat(UriExtension.of("/user/favicon.ico").getPathPrefix()).isEqualTo("./templates");
    }

}