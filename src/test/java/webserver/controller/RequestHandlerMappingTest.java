package webserver.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Maps;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RequestHandlerMappingTest {

    @DisplayName("RequestHandlerMapping에 해당하는 컨트롤러 반환")
    @MethodSource("provideStringsForGetController")
    @ParameterizedTest
    void getController(String url, Controller controllerClass) {
        RequestHandlerMapping requestHandlerMapping = new RequestHandlerMapping(Maps.newHashMap());
        requestHandlerMapping.putController("/user/create", new CreateUserController());
        requestHandlerMapping.putController("/user/login", new LoginController());
        requestHandlerMapping.putController("/", new DefaultController());
        assertThat(requestHandlerMapping.getController(url)).isInstanceOf(controllerClass.getClass());

    }

    private static Stream<Arguments> provideStringsForGetController() {
        return Stream.of(
            Arguments.of("/user/create", new CreateUserController()),
            Arguments.of("/user/login", new LoginController()),
            Arguments.of("/index.html", new DefaultController()),
            Arguments.of("/", new DefaultController()),
            Arguments.of("/asdf", new ErrorController())
        );
    }
}
