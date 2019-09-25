package http.request;

import http.common.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static http.common.ContentType.FORM_URLENCODED;
import static org.junit.jupiter.api.Assertions.*;

class RequestBodyTest {
    @ParameterizedTest
    @MethodSource("provideBodyAndContentType")
    void request_body_생성(String body, String contentType) {
        assertDoesNotThrow(() -> new RequestBody(body, contentType));
    }

    private static Stream<Arguments> provideBodyAndContentType() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of(null, null),
                Arguments.of(null, FORM_URLENCODED)
        );
    }

    @Test
    void form_data_확인() {
        RequestBody requestBody = new RequestBody("id=3&pw=pass", FORM_URLENCODED);
        assertEquals(requestBody.getFormData("id"), "3");
        assertEquals(requestBody.getFormData("pw"), "pass");
        assertNull(requestBody.getFormData("name"));
        assertNull(requestBody.getFormData(null));
    }

    @Test
    void form_data_확인2() {
        RequestBody requestBody = new RequestBody("id=3", ContentType.ICO);
        assertNull(requestBody.getFormData("id"));
    }
}