package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.ViewNotFoundException;

public class FileIoUtilsTest {

    @Test
    void loadFileFromClasspath() {
        assertThatThrownBy(() -> FileIoUtils.loadFileFromClasspath("./templates/index.html"))
            .isInstanceOf(ViewNotFoundException.class)
            .hasMessage("./templates/index.html을 찾을 수 없습니다.");
    }
}
