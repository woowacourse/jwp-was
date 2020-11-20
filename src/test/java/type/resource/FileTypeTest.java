package type.resource;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import exception.NotFoundFileTypeException;

class FileTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"html,TEMPLATE", "ico,TEMPLATE", "css,STATIC", "js,STATIC", "woff,STATIC", "woff2,STATIC"})
    void find_Resource_File_Type(final String file, final ResourceType expect) {
        final FileType fileType = FileType.find(file);

        assertThat(fileType.getResourceType()).isEqualTo(expect);
    }

    @Test
    void find_Exception() {
        assertThatThrownBy(() -> FileType.find("ppt"))
                .isInstanceOf(NotFoundFileTypeException.class)
                .hasMessage("파일 형식을 찾을 수 없습니다.");
    }
}