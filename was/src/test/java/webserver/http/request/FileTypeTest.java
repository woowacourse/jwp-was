package webserver.http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class FileTypeTest {

    @DisplayName("파일 타입 생성 테스트")
    @TestFactory
    Stream<DynamicTest> of() {
        return Stream.of(
                dynamicTest("템플릿 파일 생성", this::createTemplate),
                dynamicTest("스태틱 파일 생성", this::createStatic),
                dynamicTest("생성 실패", this::invalidFileType)
        );
    }

    private void createTemplate() {
        String uri = "/index.html";
        FileType fileType = FileType.of(uri);
        assertThat(fileType).isInstanceOf(FileType.class);
        assertThat(fileType.getContentType()).isEqualTo(TemplateFile.of(uri).getContentType());
        assertThat(fileType.getClassPath()).isEqualTo("./templates");
    }

    private void createStatic() {
        String uri = "/index.css";
        FileType fileType = FileType.of(uri);
        assertThat(fileType).isInstanceOf(FileType.class);
        assertThat(fileType.getContentType()).isEqualTo(StaticFile.of(uri).getContentType());
        assertThat(fileType.getClassPath()).isEqualTo("./static");
    }

    private void invalidFileType() {
        String uri = "/a.b";
        assertThatIllegalArgumentException()
                .isThrownBy(() -> FileType.of(uri))
                .withMessage("지원하지 않는 형태의 파일입니다.");
    }
}