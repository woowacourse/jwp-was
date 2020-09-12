package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import request.MessageBody;

class MessageBodyTest {

    private MessageBody formData = new MessageBody("id=test1234&password=13579");

    @Test
    void getFormDataFromBody() {
        Map<String, String> expected = new HashMap<>();
        expected.put("id", "test1234");
        expected.put("password", "13579");

        assertThat(formData.getFormDataFromBody()).isEqualTo(expected);
    }

    // Todo: 여기서부터 예외상황에 대한 테스트 구현 시작할것

    @Test
    void getMessageBody() {
        assertThat(formData.getMessageBody()).isEqualTo("id=test1234&password=13579");
    }
}
