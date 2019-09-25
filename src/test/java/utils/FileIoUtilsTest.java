package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileIoUtilsTest {
    public static final String INDEX_PAGE_CONTENT = "국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?";

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        assertThat(new String(body)).contains(INDEX_PAGE_CONTENT);
    }
}
