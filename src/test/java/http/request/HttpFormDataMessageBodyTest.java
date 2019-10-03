package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpFormDataMessageBodyTest {

    @Test
    @DisplayName("name과 age를 키값으로 가지는 formData가 제대로 값을 반환해주는지 검증")
    void formData쿼리_테스트() {
        String body = "name=park&age=27";
        MessageBody messageBody = new HttpFormDataMessageBody(body);

        assertThat(messageBody.get("name")).isEqualTo("park");
        assertThat(messageBody.get("age")).isEqualTo("27");
    }
}