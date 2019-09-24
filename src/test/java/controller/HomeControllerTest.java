package controller;

import controller.core.Controller;
import http.request.HttpRequest;
import http.request.core.RequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static utils.UtilData.*;

class HomeControllerTest {
    private List<Object> firstLineTokens;
    private HttpRequest httpRequest;

    @Test
    @DisplayName("HomeController 매핑 판별 참 테스트")
    void isMappingTrueTest() {
        firstLineTokens = Arrays.asList(RequestMethod.of(GET_METHOD), REQUEST_GET_PATH, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, GET_REQUEST_HEADER, DATA);

        Controller controller = new HomeController();
        assertTrue(controller.isMapping(httpRequest));
    }

    @Test
    @DisplayName("HomeController 매핑 판별 거짓 테스트")
    void isMappingFalseTest() {
        firstLineTokens = Arrays.asList(RequestMethod.of(GET_METHOD), REQUEST_GET_PARAM_PATH, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, GET_REQUEST_HEADER, DATA);

        Controller controller = new HomeController();
        assertFalse(controller.isMapping(httpRequest));
    }
}