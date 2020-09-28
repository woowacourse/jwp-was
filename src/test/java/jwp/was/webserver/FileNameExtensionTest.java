package jwp.was.webserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FileNameExtensionTest {

    private static final String DEFAULT_FILE_NAME = "/index";

    @DisplayName("From 사용시 해당하는 확장자에 속한 FileNameExtension 반환")
    @Test
    void from_ExistsFileNameExtension_ReturnFileNameExtension() {
        List<FileNameExtension> fileNameExtensions
            = new ArrayList<>(Arrays.asList(FileNameExtension.values()));
        fileNameExtensions.remove(FileNameExtension.API);
        fileNameExtensions.remove(FileNameExtension.FILE);

        for (FileNameExtension fileNameExtension : fileNameExtensions) {
            for (String extension : fileNameExtension.getExtensions()) {
                String filePath = DEFAULT_FILE_NAME + extension;
                assertThat(FileNameExtension.from(filePath)).isEqualTo(fileNameExtension);
            }
        }
    }

    @DisplayName("From 사용시 확장자가 없는 경우, API를 반환")
    @ParameterizedTest
    @ValueSource(strings = {"/index", "/index.abc/abc"})
    void from_NotExistsFileNameExtension_ReturnAPI(String filePath) {
        assertThat(FileNameExtension.from(filePath)).isEqualTo(FileNameExtension.API);
    }

    @DisplayName("From 사용시 해당하는 확장자가 없는 경우, FILE을 반환")
    @ParameterizedTest
    @ValueSource(strings = {"/index.abc", "/index.bcc"})
    void from_NotMatchExtension_ReturnFILE(String filePath) {
        assertThat(FileNameExtension.from(filePath)).isEqualTo(FileNameExtension.FILE);
    }
}
