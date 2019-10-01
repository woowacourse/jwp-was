package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseBodyTest {

    @DisplayName("Response Body가 비어있는지 확인")
    @Test
    void isEmpty() {
        ResponseBody responseBody = new ResponseBody();
        assertThat(responseBody.isEmpty()).isTrue();
    }

    @DisplayName("Response Body에 내용 추가")
    @Test
    void addBody() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + "/index.html");
        ResponseBody responseBody = new ResponseBody();
        assertThat(responseBody.setBody(body)).isTrue();
        assertThat(responseBody.getContent())
                .isEqualTo(FileIoUtils.loadFileFromClasspath("./templates" + "/index.html"));
    }

    @DisplayName("Response Body의 길이 조회")
    @Test
    void getContentLength() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + "/index.html");
        ResponseBody responseBody = new ResponseBody();
        responseBody.setBody(body);
        assertThat(responseBody.getLengthOfContent()).isEqualTo(body.length);
    }
}
