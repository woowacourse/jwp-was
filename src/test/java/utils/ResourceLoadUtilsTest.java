package utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceLoadUtilsTest {
    @Test
    void hasTemplatesFile() {
        String filePath = "/index.html";
        Optional<File> result = ResourceLoadUtils.detectFile(filePath);
        assertThat(result.isPresent()).isTrue();
    }
}