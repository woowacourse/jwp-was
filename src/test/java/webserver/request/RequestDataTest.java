package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestDataTest {

    @Test
    void getSuccess() {
        RequestData requestData = getRequestData();

        assertEquals(requestData.get("left"), "whale");
        assertEquals(requestData.get("right"), "luffy");
    }

    private RequestData getRequestData() {
        RequestData requestData = new RequestData();
        requestData.put("left=whale&right=luffy");
        return requestData;
    }

    @DisplayName("해당되는 key 가 존재하지 않는 RequestData doGet 메서드는 실패한다.")
    @Test
    void getFailWhenNoKeyValuePair() {
        RequestData requestData = getRequestData();
        assertThrows(IllegalArgumentException.class, () -> requestData.get("middle"));
    }

    @DisplayName("비어있지 않은 RequestData 의 isEmpty 메서드가 false 를 반환한다.")
    @Test
    void isEmpty() {
        RequestData requestData = getRequestData();
        assertFalse(requestData.isEmpty());
    }
}