package webserver.handlermapping;

import static org.assertj.core.api.Assertions.*;
import static webserver.ServletFixture.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.User;
import webserver.controller.StaticResourceHandlers;
import webserver.controller.UserController;
import webserver.controller.annotation.RequestMapping;
import webserver.request.ServletRequest;

class HandlerMappingStrategyTest {
    HandlerMappingStrategy defaultHandlerMappingStrategy;
    HandlerMappingStrategy staticHandlerMappingStrategy;
    ServletRequest staticRequest;
    ServletRequest dynamicRequest;

    @BeforeEach
    void setUp() {
        defaultHandlerMappingStrategy = new DefaultHandlerMappingStrategy();
        staticHandlerMappingStrategy = new StaticResourceHandlerMappingStrategy();
        staticRequest = new ServletRequest(REQUEST_START_LINE_WITH_HTML, REQUEST_HEADER_WITH_HTML, REQUEST_BODY);
        dynamicRequest = new ServletRequest(REQUEST_START_LINE, REQUEST_HEADER, REQUEST_BODY);
    }

    @DisplayName("Default 전략은 Static resource는 처리 할 수 없음.")
    @Test
    void defaultPlusStaticResource() throws NoSuchMethodException {
        boolean isSupport = defaultHandlerMappingStrategy.isSupport(staticRequest,
            UserController.class.getDeclaredMethod("create", User.class).getAnnotation(RequestMapping.class));

        assertThat(isSupport).isFalse();
    }

    @DisplayName("Default 전략은 Dynamic resource는 처리 할 수 있음.")
    @Test
    void defaultWithDynamicResource() throws NoSuchMethodException {
        boolean isSupport = defaultHandlerMappingStrategy.isSupport(dynamicRequest,
            UserController.class.getDeclaredMethod("create", User.class).getAnnotation(RequestMapping.class));

        assertThat(isSupport).isTrue();
    }

    @DisplayName("Static 전략은 Static resource는 처리 할 수 있음.")
    @Test
    void staticWithStaticResource() throws NoSuchMethodException {
        boolean isSupport = staticHandlerMappingStrategy.isSupport(staticRequest,
            StaticResourceHandlers.class.getDeclaredMethod("resolve", ServletRequest.class).getAnnotation(RequestMapping.class));

        assertThat(isSupport).isTrue();
    }

    @DisplayName("Static 전략은 Dynamic resource는 처리 할 수 없음.")
    @Test
    void defaultHandlerStrategy() throws NoSuchMethodException {
        boolean isSupport = staticHandlerMappingStrategy.isSupport(dynamicRequest,
            StaticResourceHandlers.class.getDeclaredMethod("resolve", ServletRequest.class).getAnnotation(RequestMapping.class));
        assertThat(isSupport).isFalse();
    }
}