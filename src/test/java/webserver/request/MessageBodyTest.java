package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import request.MessageBody;

class MessageBodyTest {

    @Test
    void getFormDataFromBody() {
        MessageBody formData = new MessageBody("id=test1234&password=13579");

        Map<String, String> expected = new HashMap<>();
        expected.put("id", "test1234");
        expected.put("password", "13579");

        assertThat(formData.getFormDataFromBody()).isEqualTo(expected);
    }

    @Test
    void getFormDataFromBody_IfBodyIsEmpty_ThrowException() {
        MessageBody formData = new MessageBody("");

        assertThatThrownBy(formData::getFormDataFromBody)
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("message body is empty.");
    }

    @Test
    void getMessageBody() {
        MessageBody formData = new MessageBody("id=test1234&password=13579");

        assertThat(formData.getMessageBody()).isEqualTo("id=test1234&password=13579");
    }
}
