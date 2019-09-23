package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestUrlTest {

    @Test
    @DisplayName("RequestUrl에서 QueryParameter를 추출한다")
    public void extractQueryParameter() {
        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "javajigi");
        expected.put("password", "password");
        RequestUrl requestUrl = new RequestUrl("/user/create?userId=javajigi&password=password");
        assertThat(requestUrl.getQueryParameters()).isEqualTo(expected);
    }
}