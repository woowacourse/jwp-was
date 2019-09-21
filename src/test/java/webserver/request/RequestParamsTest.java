package webserver.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestParamsTest {

    private RequestParams requestParams;

    @BeforeEach
    void setUp() {
        requestParams = new RequestParams();
        requestParams.put("left=whale&right=luffy");
    }

    @DisplayName("RequestParams 의 doGet 메서드를 이용해 성공적으로 값을 가져온다.")
    @Test
    void getSuccess() {
        assertEquals(requestParams.get("left"), "whale");
    }

    @DisplayName("해당되는 key 가 존재하지 않는 RequestParams doGet 메서드는 실패한다.")
    @Test
    void getFailWhenNoKeyValuePair() {
        assertThrows(IllegalArgumentException.class, () -> requestParams.get("middle"));
    }

    @DisplayName("비어있지 않은 RequestParams 의 isEmpty 메서드가 false 를 반환한다.")
    @Test
    void isEmpty() {
        assertFalse(requestParams.isEmpty());
    }
}