package model.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MessageBodyTest {

    @Test
    @DisplayName("MessageBody 생성")
    void create() {
        String requestMessageBody = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        MessageBody messageBody = MessageBody.of(requestMessageBody);

        assertThat(messageBody).isInstanceOf(MessageBody.class);
    }

    @Test
    @DisplayName("Post 파라미터 추출 테스트")
    void extractParameters() {
        String requestMessageBody = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        MessageBody messageBody = MessageBody.of(requestMessageBody);
        Map<String, String> parameters = messageBody.extractBodyParameters();

        assertAll(
            () -> assertThat(parameters.size()).isEqualTo(4),
            () -> assertThat(parameters.get("userId")).isEqualTo("javajigi"),
            () -> assertThat(parameters.get("password")).isEqualTo("password"),
            () -> assertThat(parameters.get("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1"),
            () -> assertThat(parameters.get("email")).isEqualTo("javajigi%40slipp.net")
        );
    }
}
