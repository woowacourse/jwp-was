package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @Test
    void loadFileFromCurrentWorkingDirectory() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./out_of_static.txt");
        log.debug("file : {}", new String(body));
    }

    @Test
    void loadFileFromCurrentWorkingDirectory_WithUnnecessarySlash() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath(".///out_of_static.txt");
        log.debug("file : {}", new String(body));
    }

    @Test
    @DisplayName("CWD 디렉토리 출력해보기")
    void printCwdDirectory() throws Exception {
        String cwdPath = FileSystems.getDefault().getPath(".").normalize().toAbsolutePath().toString();
        System.out.println(String.format("cwd path: %s\n", cwdPath));
    }

    @Test
    void printRootDirectories() {
        for (Path path : FileSystems.getDefault().getRootDirectories()) {
            System.out.println(String.format("path: %s\n", path.normalize().toAbsolutePath().toString()));
        }
    }

    @Test
    @DisplayName("존재하지 않는 파일 경로, LoadingFileFailException 발생")
    void loadFileFromClasspath_notExistFilePath() throws IOException, URISyntaxException {
        String notExistFilePath = "./not_exist_file_path";

        assertThrows(LoadingFileFailException.class, () -> FileIoUtils.loadFileFromClasspath(notExistFilePath));
    }

    // 내가 생각하는 권한은 상관이 없는건가??
//    @Test
//    @DisplayName("권한 없는 파일 경로, LoadingFileFailException 발생")
//    void loadFileFromClasspath_notPrivilagedFilePath() throws IOException, URISyntaxException {
//        String notPermittedFilePath = "./not_permitted_file_path.txt";
//
//        assertThrows(LoadingFileFailException.class, () -> FileIoUtils.loadFileFromClasspath(notPermittedFilePath));
//    }

    @Test
    @DisplayName("존재하지 않는 파일 경로")
    void canUseResourceFromFilePath_notExistFilePath() throws URISyntaxException {
        String notExistFilePath = "./not_exist_file_path";

        assertThat(FileIoUtils.canUseResourceFromFilePath(notExistFilePath)).isFalse();
    }

    @Test
    @DisplayName("존재하는 파일 경로")
    void canUseResourceFromFilePath_existFilePath() throws URISyntaxException {
        String existFilePath = "./out_of_static.txt";

        assertThat(FileIoUtils.canUseResourceFromFilePath(existFilePath)).isTrue();
    }

    @Test
    void parseExtensionFromFilePath_hasExtension() {
        String filePathEndWithExtension = "./hello.html";

        assertThat(FileIoUtils.parseExtensionFromFilePath(filePathEndWithExtension)).isEqualTo("html");
    }

    @Test
    void parseExtensionFromFilePath_hasNotExtension() {
        String absoluteFilePathWithoutExtension = "/hello";
        assertThat(FileIoUtils.parseExtensionFromFilePath(absoluteFilePathWithoutExtension)).isEqualTo("");

        String relativeFilePathWithoutExtension = "./hello";
        assertThat(FileIoUtils.parseExtensionFromFilePath(relativeFilePathWithoutExtension)).isEqualTo("");
    }
}
