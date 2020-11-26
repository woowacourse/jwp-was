package controller;

import com.google.common.collect.Maps;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.controller.Controller;
import webserver.controller.DefaultController;
import webserver.controller.ErrorController;
import webserver.controller.RequestHandlerMapping;

public class RequestHandlerMappingTest {

    private static Stream<Arguments> provideStringsForGetController() {
        return Stream.of(
            Arguments.of("/user/create", new CreateUserController()),
            Arguments.of("/user/login", new LoginController()),
            Arguments.of("/index.html", DefaultController.getInstance()),
            Arguments.of("/", DefaultController.getInstance()),
            Arguments.of("/asdf", ErrorController.getInstance())
        );
    }

    @DisplayName("RequestHandlerMapping에 해당하는 컨트롤러 반환")
    @MethodSource("provideStringsForGetController")
    @ParameterizedTest
    void getController(String url, Controller controllerClass) {
        RequestHandlerMapping requestHandlerMapping = new RequestHandlerMapping(Maps.newHashMap());
        requestHandlerMapping.putController("/user/create", new CreateUserController());
        requestHandlerMapping.putController("/user/login", new LoginController());
        requestHandlerMapping.putController("/", DefaultController.getInstance());
        Assertions.assertThat(requestHandlerMapping.getController(url))
            .isInstanceOf(controllerClass.getClass());

    }
}
