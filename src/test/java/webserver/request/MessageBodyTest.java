package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import request.MessageBody;

class MessageBodyTest {

    @Test
    void findDataFromFormFormatBody() {
        MessageBody formData = new MessageBody("id=test1234&password=13579");

        assertThat(formData.findDataFromFormFormatBody("id")).isEqualTo("test1234");
        assertThat(formData.findDataFromFormFormatBody("password")).isEqualTo("13579");
    }

    @Test
    void findDataFromFormFormatBody_IfBodyIsEmpty_ThrowException() {
        MessageBody formData = new MessageBody("");

        assertThatThrownBy(() -> formData.findDataFromFormFormatBody("Content-Type"))
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("message body is empty.");
    }

    @Test
    void getMessageBody() {
        MessageBody formData = new MessageBody("id=test1234&password=13579");

        assertThat(formData.getMessageBody()).isEqualTo("id=test1234&password=13579");
    }
}
