package webserver;

import static org.assertj.core.api.Assertions.*;
import static webserver.ServletFixture.*;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.User;
import webserver.controller.IndexController;
import webserver.controller.UserController;

class DefaultHandlerMappingTest {

    @DisplayName("올바른 헨들러를 반환한다.")
    @Test
    void mapping() throws NoSuchMethodException {
        DefaultHandlerMapping defaultHandlerMapping = new DefaultHandlerMapping(Arrays.asList(IndexController.class, UserController.class));
        ServletRequest servletRequest = new ServletRequest(REQUEST_HEADER, REQUEST_BODY);
        Method findMethod = defaultHandlerMapping.mapping(servletRequest);

        assertThat(findMethod).isEqualTo(UserController.class.getMethod("create", User.class));
    }

    @DisplayName(".html로 끝나는 Path는 파싱후 적절한 핸들러에 매핑된다.")
    @Test
    void mappingHtml() throws NoSuchMethodException {
        DefaultHandlerMapping defaultHandlerMapping = new DefaultHandlerMapping(Arrays.asList(IndexController.class, UserController.class));
        ServletRequest servletRequest = new ServletRequest(REQUEST_HEADER_WITH_HTML, REQUEST_BODY);
        Method findMethod = defaultHandlerMapping.mapping(servletRequest);

        assertThat(findMethod).isEqualTo(IndexController.class.getMethod("index"));
    }
}
