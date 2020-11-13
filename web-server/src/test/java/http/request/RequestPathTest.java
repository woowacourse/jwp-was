package http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestPathTest {

    @DisplayName("RequestPath 파싱")
    @Test
    void parse_RequestPath_With_No_Params() {
        String path = "/user/create";
        RequestPath requestPath = RequestPath.from(path);
        assertThat(requestPath.getPath()).isEqualTo("/user/create");
    }

    @DisplayName("파라미터 포함한 RequestPath 파싱")
    @Test
    void parse_RequestPath() {
        String path = "/user/create?userId=sony&password=pw123&name=sony&email=sonypark0204@gmail.com";

        RequestPath requestPath = RequestPath.from(path);

        assertAll(
            () -> assertThat(requestPath.getPath()).isEqualTo("/user/create"),
            () -> assertThat(requestPath.getParameters("userId")).isEqualTo("sony"),
            () -> assertThat(requestPath.getParameters("password")).isEqualTo("pw123"),
            () -> assertThat(requestPath.getParameters("name")).isEqualTo("sony"),
            () -> assertThat(requestPath.getParameters("email")).isEqualTo("sonypark0204@gmail.com")
        );
    }

}
