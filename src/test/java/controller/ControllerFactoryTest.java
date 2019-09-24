package controller;

import controller.exception.PathNotFoundException;
import http.request.HttpRequest;
import http.request.core.RequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static utils.UtilData.*;

class ControllerFactoryTest {
    private List<Object> firstLineTokens;
    private HttpRequest httpRequest;

    @Test
    @DisplayName("컨트롤 매핑이 잘되고 있는지 테스트")
    void mappingControllerTest() {
        firstLineTokens = Arrays.asList(RequestMethod.of(GET_METHOD), REQUEST_GET_PARAM_PATH, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, GET_REQUEST_HEADER, DATA);

        assertDoesNotThrow(() -> {
            ControllerFactory.mappingController(httpRequest);
        });
    }

    @Test
    @DisplayName("컨트롤 매핑할 때 예외처리 하는지 테스트")
    void mappingControllerExceptionTest() {
        firstLineTokens = Arrays.asList(RequestMethod.of(GET_METHOD), REQUEST_WRONG_PATH, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, GET_REQUEST_HEADER, DATA);

        assertThrows(PathNotFoundException.class,
                () -> ControllerFactory.mappingController(httpRequest));
    }

}