package http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTypeTest {

    @Test
    void form_data_Content_Type_생성_테스트() {
        ContentType contentType = ContentType.of(ContentType.FROM_URLENCODED.getType());
        MessageBody messageBody = contentType.getMessageBodyGenerator().apply("hello=world");

        assertThat(messageBody.getClass()).isEqualTo(HttpFormDataMessageBody.class);
    }
}