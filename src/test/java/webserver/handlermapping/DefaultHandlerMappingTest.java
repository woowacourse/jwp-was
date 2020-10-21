package webserver.handlermapping;

import static org.assertj.core.api.Assertions.*;
import static webserver.ServletFixture.*;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.User;
import webserver.controller.IndexController;
import webserver.controller.StaticResourceHandlers;
import webserver.controller.UserController;
import webserver.request.ServletRequest;

class DefaultHandlerMappingTest {

    @DisplayName("올바른 헨들러를 반환한다.")
    @Test
    void mapping() throws NoSuchMethodException {
        DefaultHandlerMapping defaultHandlerMapping = new DefaultHandlerMapping(
            Arrays.asList(new StaticResourceHandlerMappingStrategy(), new DefaultHandlerMappingStrategy()),
            Arrays.asList(IndexController.class, UserController.class));

        ServletRequest servletRequest = new ServletRequest(REQUEST_START_LINE, REQUEST_HEADER, REQUEST_BODY);
        Method findMethod = defaultHandlerMapping.mapping(servletRequest);

        assertThat(findMethod).isEqualTo(UserController.class.getMethod("create", User.class));
    }

    @DisplayName("정적자원은 StaticResourceHandlers에서 처리된다.")
    @Test
    void mappingHtml() throws NoSuchMethodException {
        DefaultHandlerMapping defaultHandlerMapping = new DefaultHandlerMapping(
            Arrays.asList(new StaticResourceHandlerMappingStrategy(), new DefaultHandlerMappingStrategy()),
            Arrays.asList(IndexController.class, UserController.class, StaticResourceHandlers.class));

        ServletRequest servletRequest = new ServletRequest(REQUEST_START_LINE_WITH_HTML, REQUEST_HEADER, REQUEST_BODY);
        Method findMethod = defaultHandlerMapping.mapping(servletRequest);

        assertThat(findMethod).isEqualTo(StaticResourceHandlers.class.getMethod("resolve", ServletRequest.class));
    }
}
