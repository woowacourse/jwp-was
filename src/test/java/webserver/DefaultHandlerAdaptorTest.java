package webserver;

import static org.assertj.core.api.Assertions.*;
import static webserver.ServletFixture.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.User;
import webserver.controller.IndexController;
import webserver.controller.UserController;

class DefaultHandlerAdaptorTest {

    @DisplayName("INDEX 메소드를 정상 실행하고, 결과를 반환한다.")
    @Test
    void invokeIndex() throws NoSuchMethodException {
        DefaultHandlerAdaptor adaptor = new DefaultHandlerAdaptor();
        Method method = IndexController.class.getMethod("index");
        ServletRequest request = new ServletRequest(REQUEST_HEADER_WITH_HTML, REQUEST_BODY);
        DefaultHttpMessageConverter converter = new DefaultHttpMessageConverter();

        HashMap<String, String> params = new LinkedHashMap<>();
        params.put("View", "index");
        ServletResponse expected = new ServletResponse(ServletResponse.StatusCode.OK, params);
        ServletResponse response = adaptor.invoke(method, request, converter);

        assertThat(response).isEqualToComparingFieldByField(expected);
    }

    @DisplayName("USER CREATE 메소드를 정상 실행하고, 결과를 반환한다.")
    @Test
    void invokeUserCreate() throws NoSuchMethodException {
        DefaultHandlerAdaptor adaptor = new DefaultHandlerAdaptor();
        Method method = UserController.class.getMethod("create", User.class);
        ServletRequest request = new ServletRequest(REQUEST_HEADER, REQUEST_BODY);
        DefaultHttpMessageConverter converter = new DefaultHttpMessageConverter();

        HashMap<String, String> params = new LinkedHashMap<>();
        params.put("Location", "http://localhost:8080/index.html");
        ServletResponse expected = new ServletResponse(ServletResponse.StatusCode.FOUND, params);
        ServletResponse response = adaptor.invoke(method, request, converter);

        assertThat(response).isEqualToComparingFieldByField(expected);
    }
}
