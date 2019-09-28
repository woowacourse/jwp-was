package webserver.http.request.core;

import webserver.http.exception.HttpRequestMethodException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.core.RequestMethod;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.ParseString.parseFirstLine;
import static utils.UtilData.*;

class RequestMethodTest {

    @Test
    @DisplayName("Get Method 가 잘들어온다.")
    void RequestGetMethodSuccess() {
        String method = parseFirstLine(FIRST_GET_0_9_LINE)[0];
        assertTrue(RequestMethod.of(method).isGet());
    }

    @Test
    @DisplayName("Post Method 가 잘들어온다.")
    void RequestPostMethodSuccess() {
        String method = parseFirstLine(FIRST_POST_1_0_LINE)[0];
        assertTrue(RequestMethod.of(method).isPost());
    }

    @Test
    @DisplayName("Put Method 가 잘들어온다.")
    void RequestPutMethodSuccess() {
        String method = parseFirstLine(FIRST_PUT_1_1_LINE)[0];
        assertTrue(RequestMethod.of(method).isPut());
    }

    @Test
    @DisplayName("Delete Method 가 잘들어온다.")
    void RequestDeleteMethodSuccess() {
        String method = parseFirstLine(FIRST_DELETE_2_0_LINE)[0];
        assertTrue(RequestMethod.of(method).isDelete());
    }

    @Test
    @DisplayName("잘못된 method 가 들어오는 경우 예외처리 한다.")
    void RequestMethodFail() {
        String method = parseFirstLine(FIRST_WRONG_2_1_LINE)[0];
        assertThrows(HttpRequestMethodException.class, () -> RequestMethod.of(method));
    }
}