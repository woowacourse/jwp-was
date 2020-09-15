package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.MessageBody;

class MessageBodyTest {

    @Test
    @DisplayName("request body 에서 폼 형식을 따르는 데이터 값 꺼내기")
    void findDataFromFormFormatBody() {
        MessageBody formData = new MessageBody("id=test1234&password=13579");

        assertThat(formData.findDataFromFormFormatBody("id")).isEqualTo("test1234");
        assertThat(formData.findDataFromFormFormatBody("password")).isEqualTo("13579");
    }

    @Test
    @DisplayName("request body 에서 폼 형식을 따르는 데이터 값 꺼내기 - body가 비어있는 경우 예외처리")
    void findDataFromFormFormatBody_IfBodyIsEmpty_ThrowException() {
        MessageBody formData = new MessageBody("");

        assertThatThrownBy(() -> formData.findDataFromFormFormatBody("Content-Type"))
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("message body is empty.");
    }

    @Test
    @DisplayName("body 문자열 꺼내기")
    void getMessageBody() {
        MessageBody formData = new MessageBody("id=test1234&password=13579");

        assertThat(formData.getMessageBody()).isEqualTo("id=test1234&password=13579");
    }
}
