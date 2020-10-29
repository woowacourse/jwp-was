package webserver.protocol;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestBodyTest {

    final String userId = "testId";
    final String password = "testPW";
    final String name = "testName";
    final String email = "test%40test.com";

    @DisplayName("RequestBody 생성")
    @Test
    void createTest() {
        final Map<String, String> contents = new HashMap<>();
        contents.put("userId", userId);
        contents.put("password", password);
        contents.put("name", name);
        contents.put("email", email);

        final RequestBody requestBody = new RequestBody(contents);

        assertThat(new RequestBody(contents)).usingRecursiveComparison().isEqualTo(requestBody);
    }
}