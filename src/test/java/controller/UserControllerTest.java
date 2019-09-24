package controller;

import controller.core.Controller;
import http.request.HttpRequest;
import http.request.core.RequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.UtilData.*;

class UserControllerTest {
    private List<Object> firstLineTokens;
    private HttpRequest httpRequest;

    @Test
    @DisplayName("UserController 매핑 판별 참 테스트")
    void isMappingTrueTest() {
        firstLineTokens = Arrays.asList(RequestMethod.of(GET_METHOD), REQUEST_GET_PARAM_PATH, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, GET_REQUEST_HEADER, DATA);

        Controller controller = new UserController();
        assertTrue(controller.isMapping(httpRequest));
    }

    @Test
    @DisplayName("UserController 매핑 판별 거짓 테스트")
    void isMappingFalseTest() {
        firstLineTokens = Arrays.asList(RequestMethod.of(GET_METHOD), REQUEST_WRONG_PATH, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, GET_REQUEST_HEADER, DATA);

        Controller controller = new UserController();
        assertFalse(controller.isMapping(httpRequest));
    }
}